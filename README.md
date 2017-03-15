[TOC]

# XXPictureCompres 使用方式



##  添加依赖

1.在project目录的build.gradle的allprojects节点添加
```java maven { url "https://jitpack.io" }```
如下:

    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }

2.在自己Modul的build.gradle中添加```compile 'compile 'com.github.luoshihai:XXPictureCompress:V1.0.0'```
如下:

	dependencies {
		compile 'com.github.luoshihai:XXPictureCompress:V1.0.0'
	}

## 开始使用
1.第一个压缩方法:

	//第一个参数表示压缩源 bitmap
	//第二个参数表示压缩之后图片保存的路径
	//第三个参数表示压缩后图片的最大的大小 单位为 Kb
		NativeUtil.compressBitmap(bmp, mCameraFilePath, 300);
2.第二个压缩方法:

	//第一个参数表示压缩源 bitmap
	//第二个参数表示压缩之后图片保存的路径
	//第三个参数表示是否使用是否采用哈弗曼表数据计算 品质相差5-10倍
		NativeUtil.compressBitmap(bmp,mCameraFilePath,true);

OK,用起来就这么简单 效果要比安卓自带的压缩速度快 效果好太多倍