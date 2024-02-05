#!/bin/bash
set -ex
wget https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/121.0.6167.85/linux64/chromedriver-linux64.zip
sudo apt install unzip && unzip ./chromedriver-linux64.zip && sudo apt install ./chromedriver-linux64/chromedriver