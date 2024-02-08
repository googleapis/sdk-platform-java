# Copyright 2021 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# build from the root of this repo:
FROM gcr.io/cloud-devrel-public-resources/python

COPY library_generation/ /src
RUN cd /src && python -m pip install -r requirements.in
RUN cd /src && python -m pip install .

# Allow non-root users to run python
RUN chmod +rx /root/ /root/.pyenv

WORKDIR /workspace

CMD [ "/src/generate_repo.py" ]
