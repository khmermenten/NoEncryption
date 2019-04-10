#include <jni.h>
#define SQLITE_HAS_CODEC;
#include "sqlite3.h"
#include <android/log.h>
static int callback(void *NotUsed, int argc, char **argv, char **azColName) {
    int i;
    for(i = 0; i<argc; i++) {
        // verify that everything was inserted correctly
        __android_log_print(ANDROID_LOG_DEBUG, "TAG", "%s: %s\n",azColName[i], argv[i]);
    }
    return 0;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_noencryption_MainActivity_executeQuery (JNIEnv *env, jobject, jstring path, jstring query) {
    sqlite3 *db;
    char *zErrMsg;
    int rc;

    const char* cpath = (env)->GetStringUTFChars(path, nullptr);
    const char* q = (env)->GetStringUTFChars(query, nullptr);
    rc = sqlite3_open(cpath, &db);
    sqlite3_key(db,)
    if (rc != SQLITE_OK) {
        (env)->ReleaseStringUTFChars(path, cpath);
        return (env)->NewStringUTF("couldn't establish connection");
    }
    /* Execute SQL statement */
    rc = sqlite3_exec(db, q, callback, 0, &zErrMsg);
    sqlite3_close(db);
    return (env)->NewStringUTF("success");
}
