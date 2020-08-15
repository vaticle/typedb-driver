artifacts = [
  "ch.qos.logback:logback-classic",
  "ch.qos.logback:logback-core",
  "com.google.code.findbugs:annotations",
  "com.google.code.findbugs:jsr305",
  "com.google.guava:guava",
  "commons-io:commons-io",
  "io.cucumber:cucumber-java",
  "io.cucumber:cucumber-junit",
  "io.grpc:grpc-core",
  "io.grpc:grpc-netty",
  "io.grpc:grpc-protobuf",
  "io.grpc:grpc-stub",
  "io.grpc:grpc-testing",
  "io.grpc:grpc-api",
  "io.netty:netty-all",
  "io.netty:netty-codec-http2",
  "io.netty:netty-handler",
  "io.netty:netty-handler-proxy",
  "io.netty:netty-buffer",
  "io.netty:netty-codec",
  "io.netty:netty-codec-http",
  "io.netty:netty-codec-socks",
  "io.netty:netty-common",
  "io.netty:netty-transport",
  "io.netty:netty-resolver",
  "javax.annotation:javax.annotation-api",
  "junit:junit",
  "org.hamcrest:hamcrest-all",
  "org.hamcrest:hamcrest-core",
  "org.hamcrest:hamcrest-library",
  "org.mockito:mockito-core",
  "org.slf4j:jcl-over-slf4j",
  "org.slf4j:slf4j-api",
  "org.slf4j:log4j-over-slf4j",
  "org.slf4j:slf4j-simple",
  "org.zeroturnaround:zt-exec"
]

def merge_maven_overrides(org_maven_overrides, repo_maven_overrides):
    merged = {}
    for artifact in org_maven_overrides:
        org_version = org_maven_overrides[artifact]
        org_version = org_version if type(org_version) == type("") else org_version["version"]
        merged[artifact] = repo_maven_overrides.get(artifact, org_version)
    return merged

# Override libraries conflicting with versions defined in @graknlabs_dependencies
overrides = {
    "io.netty:netty-all": "4.1.38.Final",
    "io.netty:netty-codec-http2": "4.1.38.Final",
    "io.netty:netty-handler": "4.1.38.Final",
    "io.netty:netty-handler-proxy": "4.1.38.Final",
    "io.netty:netty-buffer": "4.1.38.Final",
    "io.netty:netty-codec": "4.1.38.Final",
    "io.netty:netty-codec-http": "4.1.38.Final",
    "io.netty:netty-codec-socks": "4.1.38.Final",
    "io.netty:netty-common": "4.1.38.Final",
    "io.netty:netty-transport": "4.1.38.Final",
    "io.netty:netty-resolver": "4.1.38.Final",
}
