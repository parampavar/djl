# DJL - PyTorch engine implementation

## Overview
This module contains the PyTorch implementation of the Deep Java Library (DJL) EngineProvider.

We don't recommend that developers use classes in this module directly.
Use of these classes will couple your code with PyTorch and make switching between frameworks difficult.
Even so, developers are not restricted from using engine-specific features.
For more information, see [NDManager#invoke()](https://javadoc.io/static/ai.djl/api/0.5.0/ai/djl/ndarray/NDManager.html#invoke-java.lang.String-ai.djl.ndarray.NDList-ai.djl.ndarray.NDList-ai.djl.util.PairList-).

## Documentation

The latest javadocs can be found on the [djl.ai website](https://javadoc.io/doc/ai.djl.pytorch/pytorch-engine/latest/index.html).

You can also build the latest javadocs locally using the following command:

```sh
./gradlew javadoc
```
The javadocs output is built in the `build/doc/javadoc` folder.

## Installation
You can pull the PyTorch engine from the central Maven repository by including the following dependency:

```xml
<dependency>
    <groupId>ai.djl.pytorch</groupId>
    <artifactId>pytorch-engine</artifactId>
    <version>0.5.0</version>
    <scope>runtime</scope>
</dependency>
```
Besides the `pytorch-engine` library, you may also need to include the PyTorch native library in your project.
All current provided PyTorch native libraries are downloaded from [PyTorch C++ distribution](https://pytorch.org/get-started/locally/#start-locally).

Choose a native library based on your platform and needs:

### Automatic (Recommended)

We offer an automatic option that will download the jars the first time you run DJL.
It will automatically determine the appropriate jars for your system based on the platform and GPU support.

```xml
    <dependency>
      <groupId>ai.djl.pytorch</groupId>
      <artifactId>pytorch-native-auto</artifactId>
      <version>1.4.0</version>
      <scope>runtime</scope>
    </dependency>
```

### macOS
For macOS, you can use the following library:

- ai.djl.pytorch:pytorch-native-cpu:1.4.0:osx-x86_64

```xml
    <dependency>
      <groupId>ai.djl.pytorch</groupId>
      <artifactId>pytorch-native-cpu</artifactId>
      <classifier>osx-x86_64</classifier>
      <version>1.4.0</version>
      <scope>runtime</scope>
    </dependency>
```

### Linux
For the Linux platform, you can choose between CPU, GPU. If you have Nvidia [CUDA](https://en.wikipedia.org/wiki/CUDA)
installed on your GPU machine, you can use one of the following library:

GPU:
- ai.djl.pytorch:pytorch-native-cu101:1.4.0:linux-x86_64 - CUDA 10.1
- ai.djl.pytorch:pytorch-native-cu92:1.4.0:linux-x86_64 - CUDA 9.2

```xml
    <dependency>
      <groupId>ai.djl.pytorch</groupId>
      <artifactId>pytorch-native-cu101</artifactId>
      <classifier>linux-x86_64</classifier>
      <version>1.4.0</version>
      <scope>runtime</scope>
    </dependency>
```

```xml
    <dependency>
      <groupId>ai.djl.pytorch</groupId>
      <artifactId>pytorch-native-cu92</artifactId>
      <classifier>linux-x86_64</classifier>
      <version>1.4.0</version>
      <scope>runtime</scope>
    </dependency>
```

CPU
- ai.djl.pytorch:pytorch-native-cpu:1.4.0:linux-x86_64

```xml
    <dependency>
      <groupId>ai.djl.pytorch</groupId>
      <artifactId>pytorch-native-cpu</artifactId>
      <classifier>linux-x86_64</classifier>
      <scope>runtime</scope>
      <version>1.4.0</version>
    </dependency>
```

### Windows

For the Windows platform, you can choose between CPU and GPU.

GPU:

- ai.djl.pytorch:pytorch-native-cu101:1.4.0:win-x86_64
- ai.djl.pytorch:pytorch-native-cu92:1.4.0:win-x86_64

```xml
    <dependency>
      <groupId>ai.djl.pytorch</groupId>
      <artifactId>pytorch-native-cu101</artifactId>
      <classifier>win-x86_64</classifier>
      <version>1.4.0</version>
      <scope>runtime</scope>
    </dependency>
```

```xml
    <dependency>
      <groupId>ai.djl.pytorch</groupId>
      <artifactId>pytorch-native-cu92</artifactId>
      <classifier>win-x86_64</classifier>
      <version>1.4.0</version>
      <scope>runtime</scope>
    </dependency>
```

CPU
- ai.djl.pytorch:pytorch-native-cpu:1.4.0:win-x86_64

```xml
    <dependency>
      <groupId>ai.djl.pytorch</groupId>
      <artifactId>pytorch-native-cpu</artifactId>
      <classifier>win-x86_64</classifier>
      <scope>runtime</scope>
      <version>1.4.0</version>
    </dependency>
```
