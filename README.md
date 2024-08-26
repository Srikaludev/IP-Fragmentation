# IP Fragment Processing Application

This Java application processes network fragments, detecting issues like missing fragments, duplicate fragments, and mismatched offsets. It reads input data from a file, processes the fragments, and outputs the results to another file.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Usage](#usage)
  - [Input File Format](#input-file-format)
  - [Output File Format](#output-file-format)
- [Setup](#setup)
- [Running the Program](#running-the-program)
- [Example](#example)
- [License](#license)

## Overview

In computer networks, data is often broken down into smaller fragments for transmission. These fragments must be reassembled at the destination. This application processes these fragments, identifies potential issues, and outputs a summary of the results.

## Features

- **Fragment Sorting:** Fragments are sorted based on IP address and ID.
- **Error Detection:** The program checks for:
  - Duplicate fragments
  - Missing fragments
  - Mismatched fragment offsets
- **Flexible Input:** Can process up to 100 fragments.
- **File Handling:** Reads from `input.txt` and writes results to `output.txt`.

## Usage

### Input File Format

The input file should be named `input.txt` and should be located in the root directory of the project. The file should contain the following columns:

1. **IP Address** (String): Source IP address.
2. **ID** (Integer): Fragment ID.
3. **Fragment Offset** (Integer): Offset of the fragment.
4. **MF** (Integer): More Fragments (MF) flag (0 or 1).
5. **Payload Length** (Integer): Length of the payload.

### Output File Format

The output is written to `output.txt` and includes the following columns:

1. **IP Address** (String): Source IP address.
2. **ID** (Integer): Fragment ID.
3. **Result** (String or Integer): 
   - Total payload length if fragments are correctly assembled.
   - Descriptive error message for any issues detected.

## Setup

1. **Ensure you have Java installed** on your system.
2. **Clone or download** this repository.
3. **Place the `input.txt` file** in the project root directory with the correct format.

## Running the Program

1. **Compile the Java code**:
   ```bash
   javac processFragments.java
   java processFragments.java

2.**Feel free to use the input and output files**
