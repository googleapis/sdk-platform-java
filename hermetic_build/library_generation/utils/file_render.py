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
import os
from jinja2 import Environment, FileSystemLoader

script_dir = os.path.dirname(os.path.realpath(__file__))
jinja_env = Environment(loader=FileSystemLoader(f"{script_dir}/../templates"))


def render(template_name: str, output_name: str, **kwargs):
    template = jinja_env.get_template(template_name)
    t = template.stream(kwargs)
    directory = os.path.dirname(output_name)
    os.makedirs(directory, exist_ok=True)
    t.dump(str(output_name))


def render_to_str(template_name: str, **kwargs) -> str:
    """
    Renders a Jinja2 template and returns the output as a string.

    Args:
        template_name: The name of the Jinja2 template file.
        **kwargs: Keyword arguments containing the data to pass to the template.

    Returns:
        The rendered template content as a string.
    """
    template = jinja_env.get_template(template_name)
    rendered_content = template.render(**kwargs)
    return rendered_content
