Steps to reproduce
==================

1. Clone this repo
2. Run `bin/gradle clean generateMainProtos`

Expected: build passes

Actual: build fails with error:

```
* What went wrong:
Execution failed for task ':generateMainProtos'.
> unable to find period.proto
    searching 0 proto paths:

    for file /Users/kartikaya/zspace/wire-bugs/20240226-cc/src/main/proto/dinosaur.proto
  unable to resolve Period
    for field period (/Users/kartikaya/zspace/wire-bugs/20240226-cc/src/main/proto/dinosaur.proto:6:3)
    in message Dinosaur (/Users/kartikaya/zspace/wire-bugs/20240226-cc/src/main/proto/dinosaur.proto:5:1)
```

Workarounds
===========

1. Disable configuration caching in `gradle.properties`. Then the build passes.
2. In the build.gradle.kts file, add `tasks.matching { it.name == "generateMainProtos" }.configureEach { notCompatibleWithConfigurationCache("wiretask not cc-compatible") }`. Then the build passes.
3. Downgrade `gradle` by running `bin/hermit install gradle-7.6.3`. Also then the build passes.


Theories
========

- WireTask is not configuration-cache compatible, at least in gradle 8.6 and other versions of gradle 8+.
- This is likely related to https://github.com/square/wire/issues/2815

Notes
=====

This repo uses hermit to manage tooling, so that everybody running it gets the same tool versions. You can add a gradle wrapper if you like, you should get the same results using that.
