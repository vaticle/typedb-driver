"Generated by paket2bazel"

load(":paket.csharp_deps.bzl", _csharp_deps = "csharp_deps")

def _csharp_deps_impl(_ctx):
    _csharp_deps()

csharp_deps_extension = module_extension(
    implementation = _csharp_deps_impl,
)
