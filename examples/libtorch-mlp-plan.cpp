/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

#include <torch/torch.h>

class IrisClassifierPlan final : public torch::nn::Module {
public:
    IrisClassifierPlan()
        : dense1(register_module("dense1", torch::nn::Linear(4, 8))),
          dense2(register_module("dense2", torch::nn::Linear(8, 3))) {
    }

    torch::Tensor execute(const torch::Tensor& input) {
        torch::Tensor hidden_pre_activation = dense1->forward(input);
        torch::Tensor hidden_activation = torch::relu(hidden_pre_activation);
        return dense2->forward(hidden_activation);
    }

private:
    torch::nn::Linear dense1;
    torch::nn::Linear dense2;
};
