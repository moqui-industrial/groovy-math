#!/usr/bin/env python3
"""Keep the Moqui schema identical in moqui-math and in groovy-math's resources.

The XML files under src/main/resources/moqui-math are verbatim copies of the ones in
moqui-math and moqui-framework. Neither side is privileged: a schema change may be made
in whichever checkout is open, and this script moves it to the other and records which
direction it went.

  ./gradlew syncMoquiSchema    upstream -> vendored resources (the usual direction)
  ./gradlew pushMoquiSchema    vendored resources -> upstream (when edited here)
  ./gradlew checkMoquiSchema   fail on any difference, naming the side that changed

A difference is reported, never guessed at: whichever file is newer is named so the
author can choose, because "newer" is not the same as "right".

Upstream locations are resolved from, in order: an explicit environment variable,
then the layout used by the CI checkout, then a sibling checkout next to this repo.
Sources that cannot be found are skipped with a warning, never an error: a plain
clone of groovy-math has no Moqui checkout at all and must still build.
"""
import argparse
import datetime
import hashlib
import os
import shutil
import sys

REPO = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
TARGET = os.path.join(REPO, 'src', 'main', 'resources', 'moqui-math')
MANIFEST = os.path.join(TARGET, 'SCHEMA-PROVENANCE.properties')

# filename -> (env var naming the component root, path within it, human-readable origin)
SOURCES = {
    'MathEntities.xml': ('MOQUI_MATH_HOME', 'entity/MathEntities.xml',
                         'moqui/moqui-math @ entity/MathEntities.xml'),
    'MathViewEntities.xml': ('MOQUI_MATH_HOME', 'entity/MathViewEntities.xml',
                             'moqui/moqui-math @ entity/MathViewEntities.xml'),
    'MathData.xml': ('MOQUI_MATH_HOME', 'data/MathData.xml',
                     'moqui/moqui-math @ data/MathData.xml'),
    'BasicEntities.xml': ('MOQUI_FRAMEWORK_HOME', 'framework/entity/BasicEntities.xml',
                          'moqui/moqui-framework @ framework/entity/BasicEntities.xml'),
}

FALLBACK_ROOTS = {
    # CI checks moqui-math out beside the workspace; developers keep a full Moqui tree.
    'MOQUI_MATH_HOME': [
        os.path.join(REPO, 'moqui-math'),
        os.path.join(REPO, '..', 'moqui-math'),
        os.path.join(REPO, '..', '..', 'moqui', 'tests', 'ai', 'moqui-framework',
                     'runtime', 'component', 'moqui-math'),
    ],
    'MOQUI_FRAMEWORK_HOME': [
        os.path.join(REPO, 'moqui-framework'),
        os.path.join(REPO, '..', 'moqui-framework'),
        os.path.join(REPO, '..', '..', 'moqui', 'tests', 'ai', 'moqui-framework'),
    ],
}


def resolve_root(env_var):
    explicit = os.environ.get(env_var)
    if explicit:
        return explicit if os.path.isdir(explicit) else None
    for candidate in FALLBACK_ROOTS[env_var]:
        if os.path.isdir(candidate):
            return os.path.abspath(candidate)
    return None


def digest(path):
    with open(path, 'rb') as handle:
        return hashlib.sha256(handle.read()).hexdigest()


def write_manifest(entries):
    lines = [
        '# Canonical Moqui schema vendored into Groovy Math.',
        '# Do not edit these XML files here: edit them upstream and run `./gradlew syncMoquiSchema`.',
        '# `./gradlew checkMoquiSchema` verifies them when an upstream checkout is available.',
        'synced=' + datetime.date.today().isoformat(),
        '',
    ]
    for name, (origin, sha) in entries.items():
        lines.append('%s.origin=%s' % (name, origin))
        lines.append('%s.sha256=%s' % (name, sha))
    with open(MANIFEST, 'w') as handle:
        handle.write('\n'.join(lines) + '\n')


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--check', action='store_true',
                        help='report any difference instead of copying, and name the newer side')
    parser.add_argument('--push', action='store_true',
                        help='copy the vendored resources back to the upstream checkouts')
    options = parser.parse_args()
    if options.check and options.push:
        parser.error('--check and --push are mutually exclusive')

    os.makedirs(TARGET, exist_ok=True)
    entries = {}
    drifted = []
    skipped = []

    for name, (env_var, relative, origin) in SOURCES.items():
        vendored = os.path.join(TARGET, name)
        root = resolve_root(env_var)
        upstream = os.path.join(root, relative) if root else None

        if upstream is None or not os.path.isfile(upstream):
            skipped.append('%s (no %s)' % (name, env_var))
            if os.path.isfile(vendored):
                entries[name] = (origin, digest(vendored))
            continue

        if options.check:
            if not os.path.isfile(vendored):
                drifted.append('%s (missing here)' % name)
            elif digest(vendored) != digest(upstream):
                newer = 'here' if os.path.getmtime(vendored) > os.path.getmtime(upstream) else 'upstream'
                drifted.append('%s (differs; %s is newer)' % (name, newer))
            entries[name] = (origin, digest(vendored) if os.path.isfile(vendored) else '')
        elif options.push:
            if not os.path.isfile(vendored):
                skipped.append('%s (nothing vendored to push)' % name)
                continue
            shutil.copyfile(vendored, upstream)
            entries[name] = (origin, digest(vendored))
            print('pushed %-22s -> %s' % (name, upstream))
        else:
            shutil.copyfile(upstream, vendored)
            entries[name] = (origin, digest(vendored))
            print('synced %-22s <- %s' % (name, upstream))

    if skipped:
        print('skipped: ' + ', '.join(skipped), file=sys.stderr)

    if options.check:
        if drifted:
            print('Schema differs between moqui-math and groovy-math:', file=sys.stderr)
            for entry in drifted:
                print('  ' + entry, file=sys.stderr)
            print('Run ./gradlew syncMoquiSchema to take the upstream version, '
                  'or ./gradlew pushMoquiSchema to publish the version here.', file=sys.stderr)
            return 1
        print('Schema identical on both sides (%d checked).' % (len(SOURCES) - len(skipped)))
        return 0

    write_manifest(entries)
    print('Wrote ' + os.path.relpath(MANIFEST, REPO))
    return 0


if __name__ == '__main__':
    sys.exit(main())
