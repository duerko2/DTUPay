#!/bin/bash

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"

# Change to the script's directory
cd "$SCRIPT_DIR"

# Convert all .sh files in the current directory
dos2unix *.sh
dos2unix */*.sh


./build_and_run.sh