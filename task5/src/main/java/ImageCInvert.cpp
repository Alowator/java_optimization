#include "ImageC.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct ImageFields {
    jint width;
    jint height;
    jint* pixels;
};

ImageFields image;

JNIEXPORT void JNICALL Java_ImageC_init(JNIEnv *env, jobject obj, jint width, jint height) {
    image.width = width;
    image.height = height;
    image.pixels = new jint[width * height * 3];
//    for (int i = 0; i < width; i++) {
//        for (int j = 0; j < height; j++) {
//            for (int q = 0; q < 3; q++) {
//                image.pixels[(j * image.width + i) * 3 + q] = 0;
//            }
//        }
//    }
    //memset(image.pixels, 0, sizeof(jint) * width * height * 3);
}

JNIEXPORT jint JNICALL Java_ImageC_getWidth(JNIEnv *env, jobject obj) {
    return image.width;
}

JNIEXPORT jint JNICALL Java_ImageC_getHeight(JNIEnv *env, jobject obj) {
    return image.height;
}

JNIEXPORT jint JNICALL Java_ImageC_getRed(JNIEnv *env, jobject obj, jint x, jint y) {
    return image.pixels[(y * image.width + x) * 3 + 0];
}

JNIEXPORT jint JNICALL Java_ImageC_getGreen(JNIEnv *env, jobject obj, jint x, jint y) {
    return image.pixels[(y * image.width + x) * 3 + 1];
}

JNIEXPORT jint JNICALL Java_ImageC_getBlue(JNIEnv *env, jobject obj, jint x, jint y) {
    return image.pixels[(y * image.width + x) * 3 + 2];
}

JNIEXPORT void JNICALL Java_ImageC_setRed(JNIEnv *env, jobject obj, jint x, jint y, jint value) {
    image.pixels[(y * image.width + x) * 3 + 0] = value;
}

JNIEXPORT void JNICALL Java_ImageC_setGreen(JNIEnv *env, jobject obj, jint x, jint y, jint value) {
    image.pixels[(y * image.width + x) * 3 + 1] = value;
}

JNIEXPORT void JNICALL Java_ImageC_setBlue(JNIEnv *env, jobject obj, jint x, jint y, jint value) {
    image.pixels[(y * image.width + x) * 3 + 2] = value;
}

JNIEXPORT void JNICALL Java_ImageC_applyBlurFilter(JNIEnv *env, jobject obj, jdoubleArray blurMatrix) {
    jdouble* blurElements = env->GetDoubleArrayElements(blurMatrix, nullptr);
    jint* blurredPixels = new jint[image.width * image.height * 3];

    for (jint x = 0; x < image.width; x++) {
        for (jint y = 0; y < image.height; y++) {
            jint redSum = 0;
            jint greenSum = 0;
            jint blueSum = 0;

            for (jint i = -1; i <= 1; i++) {
                for (jint j = -1; j <= 1; j++) {
                    jint neighborX = x + j;
                    jint neighborY = y + i;

                    if (neighborX >= 0 && neighborX < image.width && neighborY >= 0 && neighborY < image.height) {
                        jdouble blurFactor = blurElements[(i + 1) * 3 + (j + 1)];
                        redSum += Java_ImageC_getRed(env, obj, neighborX, neighborY) * blurFactor;
                        greenSum += Java_ImageC_getGreen(env, obj, neighborX, neighborY) * blurFactor;
                        blueSum += Java_ImageC_getBlue(env, obj, neighborX, neighborY) * blurFactor;
                    }
                }
            }

            blurredPixels[(y * image.width + x) * 3] = static_cast<jint>(redSum);
            blurredPixels[(y * image.width + x) * 3 + 1] = static_cast<jint>(greenSum);
            blurredPixels[(y * image.width + x) * 3 + 2] = static_cast<jint>(blueSum);
        }
    }

    memcpy(image.pixels, blurredPixels, image.width * image.height * 3 * sizeof(jint));

    env->ReleaseDoubleArrayElements(blurMatrix, blurElements, JNI_ABORT);
    delete[] blurredPixels;
}