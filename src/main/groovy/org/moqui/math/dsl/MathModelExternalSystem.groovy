/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelExternalSystem
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelExternalSystem implements DslEnumValue {
    HfHub('MmesHfHub', 'HF_HUB', 'Hugging Face Hub', ''),
    OnnxZoo('MmesOnnxZoo', 'ONNX_ZOO', 'ONNX Model Zoo', ''),
    PyTorchHub('MmesPyTorchHub', 'PYTORCH_HUB', 'PyTorch Hub', ''),
    TfHub('MmesTfHub', 'TF_HUB', 'TensorFlow Hub / Kaggle Models', ''),
    NvidiaNgc('MmesNvidiaNgc', 'NVIDIA_NGC', 'NVIDIA NGC Model Catalog', ''),
    OpenAi('MmesOpenAi', 'OPENAI_API', 'OpenAI API', ''),
    Anthropic('MmesAnthropic', 'ANTHROPIC_API', 'Anthropic API', ''),
    AzureOpenAi('MmesAzureOpenAi', 'AZURE_OPENAI', 'Azure OpenAI Service', ''),
    GoogleAi('MmesGoogleAi', 'GOOGLE_AI', 'Google AI / Vertex AI / Gemini API', ''),
    AwsBedrock('MmesAwsBedrock', 'AWS_BEDROCK', 'AWS Bedrock', ''),
    Cohere('MmesCohere', 'COHERE_API', 'Cohere API', ''),
    Mistral('MmesMistral', 'MISTRAL_API', 'Mistral API', ''),
    Ollama('MmesOllama', 'OLLAMA', 'Ollama Local Runtime', ''),
    Vllm('MmesVllm', 'VLLM', 'vLLM Server', ''),
    LmStudio('MmesLmStudio', 'LM_STUDIO', 'LM Studio', ''),
    LlamaCpp('MmesLlamaCpp', 'LLAMA_CPP', 'llama.cpp Server', ''),
    Mlflow('MmesMlflow', 'MLFLOW', 'MLflow Model Registry', ''),
    Wandb('MmesWandb', 'WANDB', 'Weights and Biases', ''),
    Neptune('MmesNeptune', 'NEPTUNE', 'Neptune.ai', ''),
    Dvc('MmesDvc', 'DVC', 'DVC / DAGsHub', ''),
    Internal('MmesInternal', 'INTERNAL', 'Internal Registry', ''),
    Custom('MmesCustom', 'CUSTOM', 'Custom / Other', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelExternalSystem(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static MathModelExternalSystem fromId(final String id) {
        if (id == null) return null
        for (MathModelExternalSystem val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelExternalSystem fromCode(final String code) {
        if (code == null) return null
        for (MathModelExternalSystem val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MathModelExternalSystem fromName(final String name) {
        if (name == null) return null
        for (MathModelExternalSystem val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('HuggingFaceHub'.equalsIgnoreCase(name)) return HfHub
        if ('OnnxModelZoo'.equalsIgnoreCase(name)) return OnnxZoo
        if ('PytorchHub'.equalsIgnoreCase(name)) return PyTorchHub
        if ('TensorflowHubKaggleModels'.equalsIgnoreCase(name)) return TfHub
        if ('NvidiaNgcModelCatalog'.equalsIgnoreCase(name)) return NvidiaNgc
        if ('OpenaiApi'.equalsIgnoreCase(name)) return OpenAi
        if ('AnthropicApi'.equalsIgnoreCase(name)) return Anthropic
        if ('AzureOpenaiService'.equalsIgnoreCase(name)) return AzureOpenAi
        if ('GoogleAiVertexAiGeminiApi'.equalsIgnoreCase(name)) return GoogleAi
        if ('CohereApi'.equalsIgnoreCase(name)) return Cohere
        if ('MistralApi'.equalsIgnoreCase(name)) return Mistral
        if ('OllamaLocalRuntime'.equalsIgnoreCase(name)) return Ollama
        if ('VllmServer'.equalsIgnoreCase(name)) return Vllm
        if ('LlamaCppServer'.equalsIgnoreCase(name)) return LlamaCpp
        if ('MlflowModelRegistry'.equalsIgnoreCase(name)) return Mlflow
        if ('WeightsAndBiases'.equalsIgnoreCase(name)) return Wandb
        if ('NeptuneAi'.equalsIgnoreCase(name)) return Neptune
        if ('DvcDagshub'.equalsIgnoreCase(name)) return Dvc
        if ('InternalRegistry'.equalsIgnoreCase(name)) return Internal
        if ('CustomOther'.equalsIgnoreCase(name)) return Custom
        null
    }
}
