#!/bin/bash
set -ex
wget https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/123.0.6272.2/linux64/chromedriver-linux64.zip
sudo apt install unzip && unzip ./chromedriver-linux64.zip && sudo apt install ./chromedriver