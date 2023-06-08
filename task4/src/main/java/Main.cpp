#include "Main.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct mystruct {
    int value;
};

// функция, которая сожирает всю доступную память
JNIEXPORT void JNICALL Java_Main_allocateMemory(JNIEnv *env, jclass cls) {
    printf("Allocating memory...\n");
    int size = 1024 * 1024;
    int mbAllocated = 0;
    while (mbAllocated < 1000) {
        int *a = static_cast<int*>(malloc(size));
        if (a == nullptr)
            break;
        for (int i = 0; i < size / sizeof(int); i++)
            a[i] = i;
        mbAllocated++;
        printf("Allocated: %d MB\n", mbAllocated);
    }
}

// функция, которая возвращает длину строки
JNIEXPORT jint JNICALL Java_Main_getStringLength(JNIEnv *env, jclass cls, jstring str) {
    const char* chars = env->GetStringUTFChars( str, NULL);
    int length = strlen(chars);
    env->ReleaseStringUTFChars(str, chars);
    return length;
}

// функция, которая создает объект и возвращает его
JNIEXPORT jobject JNICALL Java_Main_createObject(JNIEnv *env, jclass obj) {
    jclass cls = env->FindClass("Main");
    jmethodID constructor = env->GetMethodID(cls, "<init>", "()V");
    jobject mainInstance = env->NewObject(cls, constructor);
    jmethodID mainTest = env->GetMethodID(cls, "testMethod", "()V");
    env->CallVoidMethod(mainInstance, mainTest);
    return mainInstance;
}

// функция, которая изменяет значение java-поля объекта
JNIEXPORT void JNICALL Java_Main_setObjectField(JNIEnv *env, jclass cls, jobject obj, jint value) {
    jfieldID field = env->GetFieldID(cls, "testField", "I");
    env->SetIntField(obj, field, value);
}

// функция, которая аллоцирует структуру и возвращает указатель
JNIEXPORT jlong JNICALL Java_Main_allocateStruct(JNIEnv *env, jclass cls) {
    struct mystruct *s = (struct mystruct *) malloc(sizeof(struct mystruct));
    s->value = 100;
    return (jlong) s;
}

// функция, которая получает указатель на структуру и возвращает значение изнутри
JNIEXPORT jint JNICALL Java_Main_getStructValue(JNIEnv* env, jclass cls, jlong p) {
    struct mystruct* s = (struct mystruct*) p;
    return s->value;
}

// функция, которая освобождает память, выделенную для структуры
JNIEXPORT void JNICALL Java_Main_freeStruct(JNIEnv* env, jclass cls, jlong p) {
    struct mystruct* s = (struct mystruct*) p;
    free(s);
    printf("free() called\n");
}

// функция, которая делит на ноль
JNIEXPORT jint JNICALL Java_Main_divideByZero(JNIEnv *env, jclass cls) {
    printf("Dividing by zero...\n");
    int x = 10;
    int y = 0;
    return x / y;
}
