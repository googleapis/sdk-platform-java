# Local Development
## Installing prerequisites
### Install synthtool
```bash
git clone https://github.com/googleapis/synthtool
cd synthtool
python3 -m pip install --require-hashes -r requirements.txt
python3 -m pip install --no-deps -e .
python -m synthtool --version
```
### Install the owl-bot CLI
```bash
git clone https://github.com/googleapis/repo-automation-bots
cd repo-automation-bots/packages/owl-bot
npm i && npm run compile && npm link
owl-bot copy-code --version
```
