plugins {
  id("com.squareup.wire") version "4.9.7"
  id("org.jetbrains.kotlin.jvm") version "1.9.22"
}

val genProtosDir = "$buildDir/generated/proto"

tasks.register("genProto") {
  val outFile = project.file("$genProtosDir/period.proto")
  outputs.file(outFile)
  doLast {
    outFile.writeText("""
      syntax = "proto2";

      enum Period {
        CRETACEOUS = 1;
      }
    """)
  }
}

tasks.matching { it.name == "generateMainProtos" }.configureEach {
  dependsOn("genProto")
}

wire {
  sourcePath {
    srcDir("src/main/proto/")
  }
  sourcePath {
    srcDir(genProtosDir)
  }
  kotlin {
  }
}
