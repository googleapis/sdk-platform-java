# Changelog

## [2.31.0](https://github.com/googleapis/sdk-platform-java/compare/v2.30.0...v2.31.0) (2024-01-02)


### Features

* [common-protos,common-protos] add auto_populated_fields to google.api.MethodSettings ([#2273](https://github.com/googleapis/sdk-platform-java/issues/2273)) ([d9be11c](https://github.com/googleapis/sdk-platform-java/commit/d9be11c7a127452c5e5ef871854a4a65c68b1b34))
* add auto_populated_fields to google.api.MethodSettings ([d9be11c](https://github.com/googleapis/sdk-platform-java/commit/d9be11c7a127452c5e5ef871854a4a65c68b1b34))
* add parsing of autopopulated fields from serviceyaml ([#2312](https://github.com/googleapis/sdk-platform-java/issues/2312)) ([4f535a7](https://github.com/googleapis/sdk-platform-java/commit/4f535a7829f98fe79053e62e22deaf91a97ab917))
* Full Endpoint Resolution from EndpointContext ([#2313](https://github.com/googleapis/sdk-platform-java/issues/2313)) ([f499ced](https://github.com/googleapis/sdk-platform-java/commit/f499ced28a562cbb3ea49f14a4fa16eb6a8173cc))
* move Java Owlbot into this repository for postprocessing ([#2282](https://github.com/googleapis/sdk-platform-java/issues/2282)) ([f8969d2](https://github.com/googleapis/sdk-platform-java/commit/f8969d2b5b50b338967802436bac8d21c3656e07))
* Parse Host Service Name ([#2300](https://github.com/googleapis/sdk-platform-java/issues/2300)) ([8822f3b](https://github.com/googleapis/sdk-platform-java/commit/8822f3b514bdddf028c12db7a773e80fa4f4f3a1))
* Structs mapper utility ([#2278](https://github.com/googleapis/sdk-platform-java/issues/2278)) ([da6607b](https://github.com/googleapis/sdk-platform-java/commit/da6607b17130ab045640618d505fda915ddb8e49))


### Bug Fixes

* format proto comments in Client Overview ([#2280](https://github.com/googleapis/sdk-platform-java/issues/2280)) ([4029fbd](https://github.com/googleapis/sdk-platform-java/commit/4029fbdd9ab060ef55382b1890f323f85fe3ceef))

## [2.30.0](https://github.com/googleapis/sdk-platform-java/compare/v2.29.0...v2.30.0) (2023-11-29)


### Features

* update javadocs for Client classes to include table of methods ([#2114](https://github.com/googleapis/sdk-platform-java/issues/2114)) ([c81cd0f](https://github.com/googleapis/sdk-platform-java/commit/c81cd0ffb95df7fc6d52629c770254aea60ff4fd))


### Bug Fixes

* confirm owlbot-copy succeeeded to transfer java files ([#2235](https://github.com/googleapis/sdk-platform-java/issues/2235)) ([94d1dd2](https://github.com/googleapis/sdk-platform-java/commit/94d1dd24e38b348a9727f231d6d9aa4e8635144e))
* improve information on CancellationException for LROs ([#2236](https://github.com/googleapis/sdk-platform-java/issues/2236)) ([741e40c](https://github.com/googleapis/sdk-platform-java/commit/741e40ceb0b7d5e0eceb8a90a52acf57648c0066))
* owlbot-cli image sha to be locked (hermetic) ([#2252](https://github.com/googleapis/sdk-platform-java/issues/2252)) ([6c4c236](https://github.com/googleapis/sdk-platform-java/commit/6c4c2364c1798642a19c8208c09c9becd562012d))


### Dependencies

* update dependency com.fasterxml.jackson:jackson-bom to v2.16.0 ([#2259](https://github.com/googleapis/sdk-platform-java/issues/2259)) ([4eef7ec](https://github.com/googleapis/sdk-platform-java/commit/4eef7ec8311cf196253143fdaa46ccdb6c8fc153))
* update dependency com.google.cloud:grpc-gcp to v1.5.0 ([#2265](https://github.com/googleapis/sdk-platform-java/issues/2265)) ([964a649](https://github.com/googleapis/sdk-platform-java/commit/964a649838b9b58c2ae40e6d7da205a9ffd17b90))
* update dependency com.google.errorprone:error_prone_annotations to v2.23.0 ([#2182](https://github.com/googleapis/sdk-platform-java/issues/2182)) ([5116f3d](https://github.com/googleapis/sdk-platform-java/commit/5116f3dcd93759220b450ebfd0a1535cb56e6e30))
* update dependency com.googlecode.maven-download-plugin:download-maven-plugin to v1.7.1 ([#2151](https://github.com/googleapis/sdk-platform-java/issues/2151)) ([cbe1bb1](https://github.com/googleapis/sdk-platform-java/commit/cbe1bb11548c791cde32d3c5b237977fa486f11a))
* update dependency cryptography to v41.0.7 ([#2255](https://github.com/googleapis/sdk-platform-java/issues/2255)) ([a98ea5c](https://github.com/googleapis/sdk-platform-java/commit/a98ea5cccc8dc8e4015473a9529f3cd066ad7913))
* update dependency net.bytebuddy:byte-buddy to v1.14.10 ([#2256](https://github.com/googleapis/sdk-platform-java/issues/2256)) ([094f263](https://github.com/googleapis/sdk-platform-java/commit/094f2639e74e483658b567ceb7125079caed7e34))
* update dependency org.apache.commons:commons-lang3 to v3.14.0 ([#2260](https://github.com/googleapis/sdk-platform-java/issues/2260)) ([03dc96b](https://github.com/googleapis/sdk-platform-java/commit/03dc96bb5f48e38436812eb882255695d256d69f))
* update dependency org.checkerframework:checker-qual to v3.40.0 ([#2221](https://github.com/googleapis/sdk-platform-java/issues/2221)) ([c3fcfbd](https://github.com/googleapis/sdk-platform-java/commit/c3fcfbd44656f7589527642056f46c21be8019a5))
* update dependency pyasn1 to v0.5.1 ([#2262](https://github.com/googleapis/sdk-platform-java/issues/2262)) ([4bfa6cf](https://github.com/googleapis/sdk-platform-java/commit/4bfa6cf7c347d8068b6cbf94788cd057686afb12))
* update google api dependencies ([#2219](https://github.com/googleapis/sdk-platform-java/issues/2219)) ([877cb2a](https://github.com/googleapis/sdk-platform-java/commit/877cb2a14a3dd9abe26b1c3a4652811c1770ccdc))
* update googleapis/java-cloud-bom digest to ab98a49 ([#2231](https://github.com/googleapis/sdk-platform-java/issues/2231)) ([67a2c94](https://github.com/googleapis/sdk-platform-java/commit/67a2c947fe6ee4bb3478b873a92d5e7c66eaa3b7))
* update grpc dependencies to v1.59.1 ([#2263](https://github.com/googleapis/sdk-platform-java/issues/2263)) ([fdb1559](https://github.com/googleapis/sdk-platform-java/commit/fdb1559013e6c2f6c6638c8ed6a078a92314f803))
* update guava monorepo ([#2139](https://github.com/googleapis/sdk-platform-java/issues/2139)) ([b861f19](https://github.com/googleapis/sdk-platform-java/commit/b861f19a824353da8f585151924813d9197b3c9b))
* update netty dependencies to v4.1.101.final ([#2257](https://github.com/googleapis/sdk-platform-java/issues/2257)) ([2733fcf](https://github.com/googleapis/sdk-platform-java/commit/2733fcf8baf33f6129cccec3f6b3a305e68ad947))
* update protobuf dependencies to v3.25.0 ([#2222](https://github.com/googleapis/sdk-platform-java/issues/2222)) ([445477f](https://github.com/googleapis/sdk-platform-java/commit/445477f7fec3a6d4be9df4c9cf3f01c007307645))
* update protobuf dependencies to v3.25.1 ([#2242](https://github.com/googleapis/sdk-platform-java/issues/2242)) ([faea6c4](https://github.com/googleapis/sdk-platform-java/commit/faea6c4414977fb5e5d7d2abc3604e01d27731c1))


### Documentation

* Update RetrySettings javadocs to include polling ([#1863](https://github.com/googleapis/sdk-platform-java/issues/1863)) ([3c9cb06](https://github.com/googleapis/sdk-platform-java/commit/3c9cb061c8cee59dd1072bd415c32110ecf10517))

## [2.29.0](https://github.com/googleapis/sdk-platform-java/compare/v2.28.0...v2.29.0) (2023-10-31)


### Features

* `generate_library.sh` with postprocessing ([#1951](https://github.com/googleapis/sdk-platform-java/issues/1951)) ([39b9f0e](https://github.com/googleapis/sdk-platform-java/commit/39b9f0e956b7967d118873ee2e365fe6a02a029b))


### Dependencies

* update dependency cryptography to v41.0.5 ([#2206](https://github.com/googleapis/sdk-platform-java/issues/2206)) ([6d1f84a](https://github.com/googleapis/sdk-platform-java/commit/6d1f84a7923573346fbfbfa3107a3da4c0a19bfe))
* update dependency google-auth to v2.23.4 ([#2217](https://github.com/googleapis/sdk-platform-java/issues/2217)) ([f1ee04d](https://github.com/googleapis/sdk-platform-java/commit/f1ee04d902000c5f8dd6a9c51dea57c9de01a25e))
* update dependency google-cloud-storage to v2.13.0 ([#2216](https://github.com/googleapis/sdk-platform-java/issues/2216)) ([1af12a8](https://github.com/googleapis/sdk-platform-java/commit/1af12a8881c2036d4ddb844c061b5f6b17e991d9))
* update google api dependencies ([#2187](https://github.com/googleapis/sdk-platform-java/issues/2187)) ([448b0d1](https://github.com/googleapis/sdk-platform-java/commit/448b0d1eea5c4bd5f89176315c21cf7d49bc1af5))
* update googleapis/java-cloud-bom digest to 41d86db ([#2205](https://github.com/googleapis/sdk-platform-java/issues/2205)) ([9152f24](https://github.com/googleapis/sdk-platform-java/commit/9152f24e7aafa165326205b12d3709c61c842a3f))
* update googleapis/java-cloud-bom digest to b8394a1 ([#2201](https://github.com/googleapis/sdk-platform-java/issues/2201)) ([f9957df](https://github.com/googleapis/sdk-platform-java/commit/f9957df04bc00d72e1a26dfd5c4c4805172d58d7))
* update googleapis/java-cloud-bom digest to d06156f ([#2200](https://github.com/googleapis/sdk-platform-java/issues/2200)) ([097e37e](https://github.com/googleapis/sdk-platform-java/commit/097e37e560646ed47925e3620c5a490a78889ec7))
* update googleapis/java-cloud-bom digest to e896c4e ([#2198](https://github.com/googleapis/sdk-platform-java/issues/2198)) ([15a796f](https://github.com/googleapis/sdk-platform-java/commit/15a796f718e7e27991d27a337223314addb0375a))
* update graal-sdk to 22.3.3 in bazel dependencies file ([#2209](https://github.com/googleapis/sdk-platform-java/issues/2209)) ([25957d3](https://github.com/googleapis/sdk-platform-java/commit/25957d3b8cc0424d5b1ac293e771a15f0fc54721))
* update grpc dependencies to v1.59.0 ([#2211](https://github.com/googleapis/sdk-platform-java/issues/2211)) ([7dafa8d](https://github.com/googleapis/sdk-platform-java/commit/7dafa8d452615e5ac5dd5fbb95e645a1ce4a9226))

## [2.28.0](https://github.com/googleapis/sdk-platform-java/compare/v2.27.0...v2.28.0) (2023-10-19)


### Features

* Add gapic options as inputs to `generate_library.sh` ([#2121](https://github.com/googleapis/sdk-platform-java/issues/2121)) ([b17d8a1](https://github.com/googleapis/sdk-platform-java/commit/b17d8a12575b273b4536b42c37bf61b321fc0e5a))
* Log DirectPath misconfiguration ([#2105](https://github.com/googleapis/sdk-platform-java/issues/2105)) ([860ae76](https://github.com/googleapis/sdk-platform-java/commit/860ae7646a985786966af55c5d3be35981e0fab1))
* Replace graal version declarion with properties placeholder ([#2120](https://github.com/googleapis/sdk-platform-java/issues/2120)) ([e5247ba](https://github.com/googleapis/sdk-platform-java/commit/e5247ba61eb4c2e543bab79110eaba3766a8264f))


### Bug Fixes

* Make sure outstanding RPCs count in ChannelPool can not go negative ([#2185](https://github.com/googleapis/sdk-platform-java/issues/2185)) ([d2d624d](https://github.com/googleapis/sdk-platform-java/commit/d2d624def3d72ecdaa3fdf7e2a92fd29d39ff1aa))


### Dependencies

* Update dependency attrs to v22.2.0 ([#2146](https://github.com/googleapis/sdk-platform-java/issues/2146)) ([49d7f55](https://github.com/googleapis/sdk-platform-java/commit/49d7f55df0efd1ebf4ec074a8b9c23324de69149))
* Update dependency cffi to v1.16.0 ([#2147](https://github.com/googleapis/sdk-platform-java/issues/2147)) ([db37d5b](https://github.com/googleapis/sdk-platform-java/commit/db37d5ba609adf571c75f9f2f51c39b7453b862b))
* Update dependency charset-normalizer to v2.1.1 ([#2148](https://github.com/googleapis/sdk-platform-java/issues/2148)) ([759e957](https://github.com/googleapis/sdk-platform-java/commit/759e957a1a3738218a6e360660d340cb99a3268e))
* Update dependency com.fasterxml.jackson:jackson-bom to v2.15.3 ([#2136](https://github.com/googleapis/sdk-platform-java/issues/2136)) ([be25555](https://github.com/googleapis/sdk-platform-java/commit/be2555565c91580df0b81ae09ad2cda8ef5a40a7))
* Update dependency com.google.auto.value:auto-value to v1.10.4 ([#2137](https://github.com/googleapis/sdk-platform-java/issues/2137)) ([5edca8b](https://github.com/googleapis/sdk-platform-java/commit/5edca8b542e767cc9a9a899420f2707a5c91e1e7))
* Update dependency com.google.auto.value:auto-value-annotations to v1.10.4 ([#2138](https://github.com/googleapis/sdk-platform-java/issues/2138)) ([705c358](https://github.com/googleapis/sdk-platform-java/commit/705c358804767419f742978185fa9a93e622a1da))
* Update dependency com.google.errorprone:error_prone_annotations to v2.22.0 ([#2130](https://github.com/googleapis/sdk-platform-java/issues/2130)) ([805daf1](https://github.com/googleapis/sdk-platform-java/commit/805daf1a9967f63b747624dd4098d78a80c083bf))
* Update dependency com.google.errorprone:error_prone_annotations to v2.22.0 ([#2145](https://github.com/googleapis/sdk-platform-java/issues/2145)) ([f936331](https://github.com/googleapis/sdk-platform-java/commit/f9363315f20f27237324a5797ba20a0097283410))
* Update dependency commons-codec:commons-codec to v1.16.0 ([#2152](https://github.com/googleapis/sdk-platform-java/issues/2152)) ([0cf3440](https://github.com/googleapis/sdk-platform-java/commit/0cf34408d97c5d7dd63ea941de27b64a95e35524))
* Update dependency cryptography to v41.0.4 [security] ([#2109](https://github.com/googleapis/sdk-platform-java/issues/2109)) ([e2f57f2](https://github.com/googleapis/sdk-platform-java/commit/e2f57f2fe20c920eafb0306ea51c8205e371d972))
* Update dependency gcp-releasetool to v1.16.0 ([#2111](https://github.com/googleapis/sdk-platform-java/issues/2111)) ([69d1259](https://github.com/googleapis/sdk-platform-java/commit/69d12593dfbd0d69d2bd79df8d42def42eccb892))
* Update dependency google-api-core to v2.12.0 ([#2153](https://github.com/googleapis/sdk-platform-java/issues/2153)) ([8c1baf5](https://github.com/googleapis/sdk-platform-java/commit/8c1baf59fac515b10698b1548a7104e8ef82cd1e))
* Update dependency google-auth to v2.23.3 ([#2154](https://github.com/googleapis/sdk-platform-java/issues/2154)) ([3645fed](https://github.com/googleapis/sdk-platform-java/commit/3645fed46926cf4f402e4986749db55b86fab6b6))
* Update dependency google-cloud-core to v2.3.3 ([#2113](https://github.com/googleapis/sdk-platform-java/issues/2113)) ([c8194cf](https://github.com/googleapis/sdk-platform-java/commit/c8194cf29dfee221d87aaaca13983794ad089433))
* Update dependency google-cloud-storage to v2.12.0 ([#2155](https://github.com/googleapis/sdk-platform-java/issues/2155)) ([0ec9027](https://github.com/googleapis/sdk-platform-java/commit/0ec902795ae36ff16890a1c02c07b2b977dfed14))
* Update dependency google-crc32c to v1.5.0 ([#2157](https://github.com/googleapis/sdk-platform-java/issues/2157)) ([6556ed5](https://github.com/googleapis/sdk-platform-java/commit/6556ed588bad9901592949ce5674ac2894102bd2))
* Update dependency google-resumable-media to v2.6.0 ([#2161](https://github.com/googleapis/sdk-platform-java/issues/2161)) ([87dd6f2](https://github.com/googleapis/sdk-platform-java/commit/87dd6f2862dbd82fe267008306e841c4e9bb5af2))
* Update dependency googleapis-common-protos to v1.61.0 ([#2156](https://github.com/googleapis/sdk-platform-java/issues/2156)) ([f7fd515](https://github.com/googleapis/sdk-platform-java/commit/f7fd515b00ff615cbd7b86c8471b2a1d8a1ce2ee))
* Update dependency gradle to v7.6.3 ([#2115](https://github.com/googleapis/sdk-platform-java/issues/2115)) ([cd9748d](https://github.com/googleapis/sdk-platform-java/commit/cd9748d4a62b843080c107309c15d6894eb0eb28))
* Update dependency importlib-metadata to v4.13.0 ([#2162](https://github.com/googleapis/sdk-platform-java/issues/2162)) ([ac6e3d5](https://github.com/googleapis/sdk-platform-java/commit/ac6e3d5bbeab2dbf35aefc14b20217119e99460d))
* Update dependency jeepney to v0.8.0 ([#2165](https://github.com/googleapis/sdk-platform-java/issues/2165)) ([59744b5](https://github.com/googleapis/sdk-platform-java/commit/59744b56409564dab142f24533672b1fa9726313))
* Update dependency jinja2 to v3.1.2 ([#2166](https://github.com/googleapis/sdk-platform-java/issues/2166)) ([c7ac765](https://github.com/googleapis/sdk-platform-java/commit/c7ac765237c7bbed041e7b15b040625f411eee5c))
* Update dependency markupsafe to v2.1.3 ([#2168](https://github.com/googleapis/sdk-platform-java/issues/2168)) ([ea26b1a](https://github.com/googleapis/sdk-platform-java/commit/ea26b1aedcdb4eb08449194b111b00e07c2c0d02))
* Update dependency net.bytebuddy:byte-buddy to v1.14.9 ([#2116](https://github.com/googleapis/sdk-platform-java/issues/2116)) ([e0ad3e6](https://github.com/googleapis/sdk-platform-java/commit/e0ad3e678ac9a42cb4c9a6111e8da750a2294ba6))
* Update dependency org.apache.commons:commons-lang3 to v3.13.0 ([#2131](https://github.com/googleapis/sdk-platform-java/issues/2131)) ([c066286](https://github.com/googleapis/sdk-platform-java/commit/c066286ef3da2f8587d44d2b3d60db63be24875a))
* Update dependency org.checkerframework:checker-qual to v3.39.0 ([#2158](https://github.com/googleapis/sdk-platform-java/issues/2158)) ([dfe2bbc](https://github.com/googleapis/sdk-platform-java/commit/dfe2bbcd6079ab0b28f091bc132dfc27c9ab98c0))
* Update dependency org.easymock:easymock to v5.2.0 ([#2159](https://github.com/googleapis/sdk-platform-java/issues/2159)) ([8daf145](https://github.com/googleapis/sdk-platform-java/commit/8daf1452351aa0df2e282676a69e90c3743b4edf))
* Update dependency org.yaml:snakeyaml to v2.2 ([#2160](https://github.com/googleapis/sdk-platform-java/issues/2160)) ([0664bf6](https://github.com/googleapis/sdk-platform-java/commit/0664bf6e578861197abd3ed31148d7db27b8eb53))
* Update dependency protobuf to v3.20.3 ([#2169](https://github.com/googleapis/sdk-platform-java/issues/2169)) ([669d369](https://github.com/googleapis/sdk-platform-java/commit/669d3698cb76471cd0c9c6a398fb7b3d848d1571))
* Update dependency pyasn1 to v0.5.0 ([#2170](https://github.com/googleapis/sdk-platform-java/issues/2170)) ([a842045](https://github.com/googleapis/sdk-platform-java/commit/a842045c71cdf88975505c90b5915eba321f0a65))
* Update dependency pyasn1-modules to v0.3.0 ([#2171](https://github.com/googleapis/sdk-platform-java/issues/2171)) ([4abbf85](https://github.com/googleapis/sdk-platform-java/commit/4abbf858712b1f629df8b19a9007d1a09451e5e2))
* Update dependency pyjwt to v2.8.0 ([#2172](https://github.com/googleapis/sdk-platform-java/issues/2172)) ([875146e](https://github.com/googleapis/sdk-platform-java/commit/875146eb3c1ac3cb1953980825f79c75c3251ea6))
* Update dependency pyparsing to v3.1.1 ([#2173](https://github.com/googleapis/sdk-platform-java/issues/2173)) ([caac831](https://github.com/googleapis/sdk-platform-java/commit/caac8316a12aa0d8b7ef5f57912727a1abfc3151))
* Update dependency typing-extensions to v4.8.0 ([#2174](https://github.com/googleapis/sdk-platform-java/issues/2174)) ([1a8f7a4](https://github.com/googleapis/sdk-platform-java/commit/1a8f7a446665b6fe03dd98856734ec48b59e080f))
* Update dependency urllib3 to v1.26.17 [security] ([#2110](https://github.com/googleapis/sdk-platform-java/issues/2110)) ([5f40056](https://github.com/googleapis/sdk-platform-java/commit/5f40056fdb5d62e59744d43dd149d5da20979e59))
* Update dependency urllib3 to v1.26.18 ([#2177](https://github.com/googleapis/sdk-platform-java/issues/2177)) ([9683111](https://github.com/googleapis/sdk-platform-java/commit/9683111627bca74ed0d123caf37ff0993c71d2ea))
* Update dependency zipp to v3.17.0 ([#2175](https://github.com/googleapis/sdk-platform-java/issues/2175)) ([1700c59](https://github.com/googleapis/sdk-platform-java/commit/1700c597a74f906689a3a53e023ac094c9deeaff))
* Update google api dependencies ([#2132](https://github.com/googleapis/sdk-platform-java/issues/2132)) ([bd4ae4f](https://github.com/googleapis/sdk-platform-java/commit/bd4ae4fab88a421ad93e597f99cabb7997f91e1d))
* Update google auth library dependencies to v1.20.0 ([#2142](https://github.com/googleapis/sdk-platform-java/issues/2142)) ([ce59ed8](https://github.com/googleapis/sdk-platform-java/commit/ce59ed8be29a6b05f7c999bf48cb4d442fc2294a))
* Update googleapis/java-cloud-bom digest to 4b9b60d ([#2178](https://github.com/googleapis/sdk-platform-java/issues/2178)) ([6a8972e](https://github.com/googleapis/sdk-platform-java/commit/6a8972ea287f665d82046b8314887edee27a6c1a))
* Update googleapis/java-cloud-bom digest to a0bfee4 ([#2181](https://github.com/googleapis/sdk-platform-java/issues/2181)) ([88bccd9](https://github.com/googleapis/sdk-platform-java/commit/88bccd9e4b6c3245c0fae7e142954155bf373274))
* Update googleapis/java-cloud-bom digest to e485c2f ([#2134](https://github.com/googleapis/sdk-platform-java/issues/2134)) ([97f97c6](https://github.com/googleapis/sdk-platform-java/commit/97f97c6063f91183af260d875d58958f4bba658b))
* Update netty dependencies ([#2141](https://github.com/googleapis/sdk-platform-java/issues/2141)) ([fedc7b9](https://github.com/googleapis/sdk-platform-java/commit/fedc7b921f5415b2d51d3147ef1aaebf1b75ccdb))
* Update protobuf dependencies to v3.24.4 ([#2118](https://github.com/googleapis/sdk-platform-java/issues/2118)) ([7b3f4b9](https://github.com/googleapis/sdk-platform-java/commit/7b3f4b97b013a32f171bd8a365d6d712b94b3849))

## [2.27.0](https://github.com/googleapis/sdk-platform-java/compare/v2.26.1...v2.27.0) (2023-10-06)


### Features

* [common-protos] public google.api.FieldInfo type and extension ([#2037](https://github.com/googleapis/sdk-platform-java/issues/2037)) ([f2b8280](https://github.com/googleapis/sdk-platform-java/commit/f2b828005e6b21a2af0e646bb8cc6d0f8c39d4e4))
* Generate proto-only library ([#2046](https://github.com/googleapis/sdk-platform-java/issues/2046)) ([f9ac8fb](https://github.com/googleapis/sdk-platform-java/commit/f9ac8fb0020372be4b62d5dffe7e1b73a1ac493f))
* More logging for ChannelPool shutdown ([#2070](https://github.com/googleapis/sdk-platform-java/issues/2070)) ([b8365c2](https://github.com/googleapis/sdk-platform-java/commit/b8365c2a0c56db1ee286c582cd9ebe31a3d614e8))


### Dependencies

* Bumping java-shared-config to 1.5.8 ([#2072](https://github.com/googleapis/sdk-platform-java/issues/2072)) ([fa5e937](https://github.com/googleapis/sdk-platform-java/commit/fa5e937e98ad19b997f8c0e11cd03e61f707f3fe))

## [2.26.1](https://github.com/googleapis/sdk-platform-java/compare/v2.26.0...v2.26.1) (2023-09-25)


### Bug Fixes

* Add Geo common protos to Bazel test dependencies. ([#2033](https://github.com/googleapis/sdk-platform-java/issues/2033)) ([625ecc5](https://github.com/googleapis/sdk-platform-java/commit/625ecc503a6b193716820681aafe0f2daf16d4c4))

## [2.26.0](https://github.com/googleapis/sdk-platform-java/compare/v2.25.0...v2.26.0) (2023-09-21)


### Features

* Filter out `*gapic.legacy.yaml` when parsing `java_gapic_opts` ([#2015](https://github.com/googleapis/sdk-platform-java/issues/2015)) ([1ad5ec0](https://github.com/googleapis/sdk-platform-java/commit/1ad5ec0c2d99e41f4fddf9ed131c6dc9bffe17b4))
* Generate showcase without post-processing ([#1935](https://github.com/googleapis/sdk-platform-java/issues/1935)) ([7218d80](https://github.com/googleapis/sdk-platform-java/commit/7218d8035946c350d7857fede93d32df3b2b8a86))
* Hermetic build OS detection ([#1988](https://github.com/googleapis/sdk-platform-java/issues/1988)) ([4fc844e](https://github.com/googleapis/sdk-platform-java/commit/4fc844ee0ffdaae26ef3d32c18bbe7604da5c16f))
* Hermetic build scripts to use a single output/generation folder ([#1987](https://github.com/googleapis/sdk-platform-java/issues/1987)) ([f5efb0e](https://github.com/googleapis/sdk-platform-java/commit/f5efb0edb097980db95832469188c754ec3f1779))
* Search gapic additional protos in `BUILD.bazel` ([#2004](https://github.com/googleapis/sdk-platform-java/issues/2004)) ([ed16ac7](https://github.com/googleapis/sdk-platform-java/commit/ed16ac74ab8430ee0def437147298d7d88da6b0c))


### Bug Fixes

* **hermetic-build:** Obtain gapic-generator-java locally on release branch ([#2023](https://github.com/googleapis/sdk-platform-java/issues/2023)) ([f3088d5](https://github.com/googleapis/sdk-platform-java/commit/f3088d55478ab049110d8c2e67ff1dd056a5e71d))
* Showcase update goal fix ([#2002](https://github.com/googleapis/sdk-platform-java/issues/2002)) ([47811f7](https://github.com/googleapis/sdk-platform-java/commit/47811f79848d6efd9dda8b3e8e4286dc3045423f))
* Skip generating `grpc-*` directory if transport is `rest` ([#1979](https://github.com/googleapis/sdk-platform-java/issues/1979)) ([9c0316b](https://github.com/googleapis/sdk-platform-java/commit/9c0316bfed890ed7d8e40340a3510d2058dc5d71))


### Documentation

* Add repo-metadata.json files for modules we publish Cloud RAD d… ([#2003](https://github.com/googleapis/sdk-platform-java/issues/2003)) ([2212ba3](https://github.com/googleapis/sdk-platform-java/commit/2212ba300c215b00cf852fd3d8615f3d3c3458e1))


### Dependencies

* Update protobuf dependencies to v3.24.3 ([#2020](https://github.com/googleapis/sdk-platform-java/issues/2020)) ([2b45049](https://github.com/googleapis/sdk-platform-java/commit/2b45049fd6fbd38acded8ae900ef4b530a441bdf))

## [2.25.0](https://github.com/googleapis/sdk-platform-java/compare/v2.24.0...v2.25.0) (2023-09-08)


### Features

* [common-protos,common-protos] add new FieldBehavior value IDENTIFIER ([#1937](https://github.com/googleapis/sdk-platform-java/issues/1937)) ([24ae2a3](https://github.com/googleapis/sdk-platform-java/commit/24ae2a3e0237fb2821ea07ccdd874c92ead598a2))
* Add `generate_library.sh` without post processing ([#1916](https://github.com/googleapis/sdk-platform-java/issues/1916)) ([ffc058a](https://github.com/googleapis/sdk-platform-java/commit/ffc058a08c523b3811b021ec1bc30201452a36c9))
* Adding vendor and vendor information in header ([#1963](https://github.com/googleapis/sdk-platform-java/issues/1963)) ([ed44aa7](https://github.com/googleapis/sdk-platform-java/commit/ed44aa785f8e21ad07aed984220db6e30e07a6c6))
* Introduce @ObsoleteApi ([#1887](https://github.com/googleapis/sdk-platform-java/issues/1887)) ([5df1aa3](https://github.com/googleapis/sdk-platform-java/commit/5df1aa3733852aaeed068080f6b48047e89dc799))


### Bug Fixes

* Remove -H:-RunReachabilityHandlersConcurrently ([#1892](https://github.com/googleapis/sdk-platform-java/issues/1892)) ([9cc52e1](https://github.com/googleapis/sdk-platform-java/commit/9cc52e1e788e4196c9e83ad5d8a9487042ae707e))
* Use && in equals check to avoid possible NPE ([#1927](https://github.com/googleapis/sdk-platform-java/issues/1927)) ([815567c](https://github.com/googleapis/sdk-platform-java/commit/815567ca4085702ecee8fc95603574cd52d6c57d))


### Documentation

* [iam] Minor formatting ([#1902](https://github.com/googleapis/sdk-platform-java/issues/1902)) ([5ec1142](https://github.com/googleapis/sdk-platform-java/commit/5ec1142458a46ae1c561f608b2ef7ac18ab0bafd))

## [2.24.0](https://github.com/googleapis/sdk-platform-java/compare/v2.23.1...v2.24.0) (2023-08-01)


### Features

* [common-protos] Add a proto message to describe the `resource_type` and `resource_permission` for an API method ([#1878](https://github.com/googleapis/sdk-platform-java/issues/1878)) ([3e27ca9](https://github.com/googleapis/sdk-platform-java/commit/3e27ca9763e9639fb00b99bdef9cf95aacd33897))

## [2.23.1](https://github.com/googleapis/sdk-platform-java/compare/v2.23.0...v2.23.1) (2023-07-19)


### Bug Fixes

* Resource name class deduplication ([#1854](https://github.com/googleapis/sdk-platform-java/issues/1854)) ([08eca7d](https://github.com/googleapis/sdk-platform-java/commit/08eca7de7e80c6f242a23a9d76659e0a413c896f))
* Use bindings for resolving multi pattern resources ([#1818](https://github.com/googleapis/sdk-platform-java/issues/1818)) ([1352fab](https://github.com/googleapis/sdk-platform-java/commit/1352fab654052e670df6d5c8718fcc6e06ef086c))


### Dependencies

* Auto-value version 1.10.2 ([#1859](https://github.com/googleapis/sdk-platform-java/issues/1859)) ([54843c9](https://github.com/googleapis/sdk-platform-java/commit/54843c95e0f0e2d004b14e82650c01026bf2ad70))
* Bump grpc version to 1.56.1 ([1fcdd89](https://github.com/googleapis/sdk-platform-java/commit/1fcdd893240a7e5da293c6a26df193131577bbdd))
* Bump jackson version to 2.15.2 ([bde81d8](https://github.com/googleapis/sdk-platform-java/commit/bde81d8c6f15130f51ea4e2d9805de3c0a21ae39))

## [2.23.0](https://github.com/googleapis/sdk-platform-java/compare/v2.22.0...v2.23.0) (2023-07-07)


### Features

* Support GDC-H Credentials  ([#1642](https://github.com/googleapis/sdk-platform-java/issues/1642)) ([26da0d3](https://github.com/googleapis/sdk-platform-java/commit/26da0d3fb882e4f11c1cc43a81d34eb500c2c0cf))


### Bug Fixes

* [gapic-generator-java] handle response and metadata type ambiguity in LRO parsing ([#1726](https://github.com/googleapis/sdk-platform-java/issues/1726)) ([1ace494](https://github.com/googleapis/sdk-platform-java/commit/1ace49499175f2509062edb2fa5098e6cf8035d6))
* Abstract batch resource and add method to determine if batch should be flushed ([#1790](https://github.com/googleapis/sdk-platform-java/issues/1790)) ([4c74107](https://github.com/googleapis/sdk-platform-java/commit/4c741077d614093d08665e9ddd83fb0e332b7881))


### Dependencies

* Bump grpc-java version to 1.55.3 ([1ff8cc0](https://github.com/googleapis/sdk-platform-java/commit/1ff8cc017bfb567933713ceeb06502f5eec9eaa6))
* Bump guava version to 32.1.1-jre ([016e84d](https://github.com/googleapis/sdk-platform-java/commit/016e84d17febd68e2aa028cba40e2781a383f7ff))
* Bump j2obc-annotations version to 2.8 ([4f45313](https://github.com/googleapis/sdk-platform-java/commit/4f4531373f313a7d68c2b9bfd5b0f1f71b21128f))
* Update google-auth-java-library to 1.19.0 ([#1815](https://github.com/googleapis/sdk-platform-java/issues/1815)) ([41007a9](https://github.com/googleapis/sdk-platform-java/commit/41007a9efbd4cf08a5f933c23e01fce6cf2efac7))

## [2.22.0](https://github.com/googleapis/sdk-platform-java/compare/v2.21.0...v2.22.0) (2023-06-22)


### Features

* Dynamically determine protobuf version in build.gradle ([#1753](https://github.com/googleapis/sdk-platform-java/issues/1753)) ([6de5110](https://github.com/googleapis/sdk-platform-java/commit/6de51100965b8b6a1c252ec06b10993486de7d44))
* GA Gax HttpJson ([#1264](https://github.com/googleapis/sdk-platform-java/issues/1264)) ([9f15fea](https://github.com/googleapis/sdk-platform-java/commit/9f15fea139e9b49ae32e6a4923085f9aca9c3115))


### Bug Fixes

* Update grpc to 1.55.1 in dependencies.properties ([#1794](https://github.com/googleapis/sdk-platform-java/issues/1794)) ([4df4450](https://github.com/googleapis/sdk-platform-java/commit/4df445067484454455512c8f7de0055c0ae21434))

## [2.21.0](https://github.com/googleapis/sdk-platform-java/compare/v2.20.1...v2.21.0) (2023-06-06)


### Features

* Dynamic Routing Headers for HttpJson ([#1667](https://github.com/googleapis/sdk-platform-java/issues/1667)) ([003b993](https://github.com/googleapis/sdk-platform-java/commit/003b993f7ad7cae8ae8c101e0ff147e517dcd83e))
* Implement awaitTermination() for MangedHttpJsonChannel ([#1677](https://github.com/googleapis/sdk-platform-java/issues/1677)) ([dea8426](https://github.com/googleapis/sdk-platform-java/commit/dea8426a85b53d08d3c8d317a1af4312889b1a3d))


### Bug Fixes

* [gapic-generator-java] align writer behavior for nested types ([#1709](https://github.com/googleapis/sdk-platform-java/issues/1709)) ([a21ffe8](https://github.com/googleapis/sdk-platform-java/commit/a21ffe88f880c0b3c6b5b512cd4d0741d9620dd3))
* [gapic-generator-java] update year to 2023 in generated license headers ([#1720](https://github.com/googleapis/sdk-platform-java/issues/1720)) ([ef4a7ff](https://github.com/googleapis/sdk-platform-java/commit/ef4a7ff91b259f0b650cc06bd664eadca7bd17c9))
* Update the return type of setWaitTimeout for convience ([#1751](https://github.com/googleapis/sdk-platform-java/issues/1751)) ([f1927a5](https://github.com/googleapis/sdk-platform-java/commit/f1927a53306ee28186074e6cb03a933a0cdffc1f))


### Dependencies

* Update dependency com.google.auth:google-auth-library-credentials and google-auth-library-oauth2-http to v1.17.0 ([#1756](https://github.com/googleapis/sdk-platform-java/issues/1756)) ([f583258](https://github.com/googleapis/sdk-platform-java/commit/f58325893b6ab306adbe4047b6dfc438de133ac7))
* Update protobuf dependencies to v3.23.2 ([#1755](https://github.com/googleapis/sdk-platform-java/issues/1755)) ([55ecbc3](https://github.com/googleapis/sdk-platform-java/commit/55ecbc35cbd735b8f4aab48e02e080d79828d70c))

## [2.20.1](https://github.com/googleapis/sdk-platform-java/compare/v2.20.0...v2.20.1) (2023-05-25)


### Bug Fixes

* Allow quota project to be used in combination with null credentials ([#1688](https://github.com/googleapis/sdk-platform-java/issues/1688)) ([cb07bd4](https://github.com/googleapis/sdk-platform-java/commit/cb07bd405b448fbef51367d1b03d4fe9eab54504))

## [2.20.0](https://github.com/googleapis/sdk-platform-java/compare/v2.19.0...v2.20.0) (2023-05-24)


### Features

* Create additional tag on release for google-cloud-shared-dependencies ([#1692](https://github.com/googleapis/sdk-platform-java/issues/1692)) ([c0ea245](https://github.com/googleapis/sdk-platform-java/commit/c0ea24588de744a46600fcf6f0f3c6414549e12d))
* Make stream wait timeout a first class citizen ([#1473](https://github.com/googleapis/sdk-platform-java/issues/1473)) ([bc8a4ad](https://github.com/googleapis/sdk-platform-java/commit/bc8a4ad864f4d6f2b9d98e5fe296320fea19e74b))


### Bug Fixes

* Fix release tag workflow to fetch all tags first ([#1700](https://github.com/googleapis/sdk-platform-java/issues/1700)) ([99b0c96](https://github.com/googleapis/sdk-platform-java/commit/99b0c967fbed22967cd072825de403ed9bac0209))
* Update tag workflow ([#1699](https://github.com/googleapis/sdk-platform-java/issues/1699)) ([a988fe7](https://github.com/googleapis/sdk-platform-java/commit/a988fe7dcfc8568c9256c22b5df907b16e5eea5a))


### Dependencies

* Update protobuf dependencies to v3.23.1 and gprc to 1.55.1 ([#1698](https://github.com/googleapis/sdk-platform-java/issues/1698)) ([1f56175](https://github.com/googleapis/sdk-platform-java/commit/1f561754ed9e797f639cbdfabe070b86b645281b))
* Update showcase version to 0.28.1 ([#1673](https://github.com/googleapis/sdk-platform-java/issues/1673)) ([9a120e9](https://github.com/googleapis/sdk-platform-java/commit/9a120e98524018fa54ae5dcbe2fb0f524c2a0f1b))

## [2.19.0](https://github.com/googleapis/gapic-generator-java/compare/v2.18.0...v2.19.0) (2023-05-09)


### Features

* [common-protos] .NET library settings (for generator behavior tweaks) ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* [common-protos] add GEO, SHOPPING, GENERATIVE_AI client orgs ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* [common-protos] Log Analytics features of the Cloud Logging API ([#1573](https://github.com/googleapis/gapic-generator-java/issues/1573)) ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Add an option to enable DirectPath xDS ([#1643](https://github.com/googleapis/gapic-generator-java/issues/1643)) ([4054bc6](https://github.com/googleapis/gapic-generator-java/commit/4054bc668daf797a8fce798843e0ec4cda669faa))
* Add ConfigServiceV2.CreateBucketAsync method for creating Log Buckets asynchronously ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Add ConfigServiceV2.CreateLink method for creating linked datasets for Log Analytics Buckets ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Add ConfigServiceV2.DeleteLink method for deleting linked datasets ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Add ConfigServiceV2.GetLink methods for describing linked datasets ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Add ConfigServiceV2.ListLinks method for listing linked datasets ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Add ConfigServiceV2.UpdateBucketAsync method for creating Log Buckets asynchronously ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Add LogBucket.analytics_enabled field that specifies whether Log Bucket's Analytics features are enabled ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Add LogBucket.index_configs field that contains a list of Log Bucket's indexed fields and related configuration data ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Remove directpath enable env ([#1657](https://github.com/googleapis/gapic-generator-java/issues/1657)) ([47851e8](https://github.com/googleapis/gapic-generator-java/commit/47851e877c868ddd6c3eb368591b4e6b635e21c1))


### Bug Fixes

* Unary Callables Deadline values respect the TotalTimeout in RetrySettings ([#1603](https://github.com/googleapis/gapic-generator-java/issues/1603)) ([d2fe520](https://github.com/googleapis/gapic-generator-java/commit/d2fe5203c4db1f8ddfd5a9566e6259b53441348a))


### Dependencies

* Update dependency org.graalvm.sdk:graal-sdk to v22.3.2 ([6107ff3](https://github.com/googleapis/gapic-generator-java/commit/6107ff316c544e4acb1d808a2ddaf9a805f9d7e7))


### Documentation

* [common-protos] mark ReservationResourceUsage field as deprecated ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* [common-protos] use deprecated=true for deprecated fields ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Documentation for the Log Analytics features of the Cloud Logging API ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Fix new_issue_uri comment ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))
* Separate paragraphs in linear/exponential distributions ([7479758](https://github.com/googleapis/gapic-generator-java/commit/7479758bcc7c7631b01a2a06d149429852cc3900))

## [2.18.0](https://github.com/googleapis/gapic-generator-java/compare/v2.17.0...v2.18.0) (2023-04-25)


### Features

* **deps:** Add gapic-generator-java to bom ([#1645](https://github.com/googleapis/gapic-generator-java/issues/1645)) ([3e85c4b](https://github.com/googleapis/gapic-generator-java/commit/3e85c4b0e7c7e7fe1df14f2ace6130ac85039368))


### Bug Fixes

* Unescape Java keyword field names when generating HttpJson unit tests. ([#1654](https://github.com/googleapis/gapic-generator-java/issues/1654)) ([5fd79ea](https://github.com/googleapis/gapic-generator-java/commit/5fd79ea433d74d27d6115d91e2e0947e7b247b5d))

## [2.17.0](https://github.com/googleapis/gapic-generator-java/compare/v2.16.0...v2.17.0) (2023-04-11)


### Features

* Add `FunctionalInterface` annotation ([#1515](https://github.com/googleapis/gapic-generator-java/issues/1515)) ([66c0509](https://github.com/googleapis/gapic-generator-java/commit/66c05093b5c56208c3f785e9cf0f5b50f5ad16d4))
* Add stream method for `ServerStream` ([#1575](https://github.com/googleapis/gapic-generator-java/issues/1575)) ([e38c8ec](https://github.com/googleapis/gapic-generator-java/commit/e38c8ec4c2fca131c91e264c7b3e2cad9243a6e5))
* Add stream methods for `Page` ([#1425](https://github.com/googleapis/gapic-generator-java/issues/1425)) ([cf0e01a](https://github.com/googleapis/gapic-generator-java/commit/cf0e01a47258c46a1b4431920b8d7cd24ae9b801))


### Bug Fixes

* Add javadoc for `ApiFutures` ([#1609](https://github.com/googleapis/gapic-generator-java/issues/1609)) ([4bb0a5e](https://github.com/googleapis/gapic-generator-java/commit/4bb0a5e3ce78878841e87a47cd457995ea9ed87e))
* **deps:** Remove unnecessary auto-value in api-common-java ([#1621](https://github.com/googleapis/gapic-generator-java/issues/1621)) ([ffeb820](https://github.com/googleapis/gapic-generator-java/commit/ffeb820ab5a530ca0f8a91d873b07b4ebaea320b))


### Dependencies

* Update dependency cryptography to v39.0.2 ([#1592](https://github.com/googleapis/gapic-generator-java/issues/1592)) ([74cf36e](https://github.com/googleapis/gapic-generator-java/commit/74cf36e628a7f0d2e00c3bf1426be9b8089df8af))
* Update dependency gcp-docuploader to v0.6.5 ([#1593](https://github.com/googleapis/gapic-generator-java/issues/1593)) ([501ee9c](https://github.com/googleapis/gapic-generator-java/commit/501ee9c61bd3f0091bf07d0e60c1dcd2319d1a17))
* Update dependency google-cloud-core to v2.3.2 ([#1595](https://github.com/googleapis/gapic-generator-java/issues/1595)) ([335a104](https://github.com/googleapis/gapic-generator-java/commit/335a104d4846623a25497f5650ea553d5cca677a))
* Update dependency net.bytebuddy:byte-buddy to v1.14.3 ([#1567](https://github.com/googleapis/gapic-generator-java/issues/1567)) ([a270ceb](https://github.com/googleapis/gapic-generator-java/commit/a270cebfeb8d1bb2270cef93f19c44935ed980cf))
* Update dependency org.threeten:threetenbp to v1.6.8 ([#1590](https://github.com/googleapis/gapic-generator-java/issues/1590)) ([26d1c3e](https://github.com/googleapis/gapic-generator-java/commit/26d1c3e511c6fa3a353c3205c06fa8dad6019715))
* Update dependency org.threeten:threetenbp to v1.6.8 ([#1591](https://github.com/googleapis/gapic-generator-java/issues/1591)) ([c3bed81](https://github.com/googleapis/gapic-generator-java/commit/c3bed81d540db51aaaa6e057133efa96b0f814a8))
* Update dependency urllib3 to v1.26.15 ([#1596](https://github.com/googleapis/gapic-generator-java/issues/1596)) ([3288fb3](https://github.com/googleapis/gapic-generator-java/commit/3288fb323d957b182bf246540f9f17157afc5285))
* Update google api dependencies ([#1578](https://github.com/googleapis/gapic-generator-java/issues/1578)) ([c537aba](https://github.com/googleapis/gapic-generator-java/commit/c537aba9d1c6cd4ecdcdc7ce0914a6c605c58b68))

## [2.16.0](https://github.com/googleapis/gapic-generator-java/compare/v2.15.3...v2.16.0) (2023-03-28)


### Features

* [common-protos] add audit_context.proto Bazel targets ([#1493](https://github.com/googleapis/gapic-generator-java/issues/1493)) ([408dba8](https://github.com/googleapis/gapic-generator-java/commit/408dba8fcfa1cbc87acf9bf6fce29ae7e7af739e))
* Install compatibility check ([#1508](https://github.com/googleapis/gapic-generator-java/issues/1508)) ([960067a](https://github.com/googleapis/gapic-generator-java/commit/960067ab60702b50e386cbc413aee2614eebf6d0))


### Bug Fixes

* **deps:** Update dependency com.google.api:gapic-generator-java-bom to v2.15.3 ([#9217](https://github.com/googleapis/gapic-generator-java/issues/9217)) ([4b6157c](https://github.com/googleapis/gapic-generator-java/commit/4b6157c482113ecec02959ad9b84242b116f3f43))
* **deps:** Update dependency com.google.http-client:google-http-client-bom to v1.43.1 ([#9213](https://github.com/googleapis/gapic-generator-java/issues/9213)) ([5d1a0e5](https://github.com/googleapis/gapic-generator-java/commit/5d1a0e54f4681ef8ff55df1da517f9a3876c57bc))
* Fix race condition in GrpcDirectStreamController ([#1537](https://github.com/googleapis/gapic-generator-java/issues/1537)) ([17d133b](https://github.com/googleapis/gapic-generator-java/commit/17d133bd625a9fe203019514aedf63e9fdad97f8))
* Use UTF-8 as default charset for HttpJson requests ([#1477](https://github.com/googleapis/gapic-generator-java/issues/1477)) ([79d986b](https://github.com/googleapis/gapic-generator-java/commit/79d986bd35b49a819dc875cfec69ec8685517930)), closes [#1437](https://github.com/googleapis/gapic-generator-java/issues/1437)
* Validate paths and check additionalPathTemplates ([#1522](https://github.com/googleapis/gapic-generator-java/issues/1522)) ([5173014](https://github.com/googleapis/gapic-generator-java/commit/5173014b041001bf6c95209404f31db39347717d))


### Dependencies

* Update dependency com.google.cloud:google-iam-policy to v1.11.0 ([#1505](https://github.com/googleapis/gapic-generator-java/issues/1505)) ([56a1104](https://github.com/googleapis/gapic-generator-java/commit/56a11046f4349f97e3c272a92cd75acfb5c9e74c))
* Update dependency org.threeten:threetenbp to v1.6.6 ([#1543](https://github.com/googleapis/gapic-generator-java/issues/1543)) ([4afc5d8](https://github.com/googleapis/gapic-generator-java/commit/4afc5d80b9a89f0b3241ce62a2348894afd34e1b))
* Update dependency org.threeten:threetenbp to v1.6.6 ([#1544](https://github.com/googleapis/gapic-generator-java/issues/1544)) ([4591e5b](https://github.com/googleapis/gapic-generator-java/commit/4591e5bb8aa99acb1719422313ca3f53345a52dd))
* Update dependency org.threeten:threetenbp to v1.6.7 ([#1550](https://github.com/googleapis/gapic-generator-java/issues/1550)) ([1df3dd2](https://github.com/googleapis/gapic-generator-java/commit/1df3dd255e4af0028f9010267a45e1cf82a85f31))
* Update dependency org.threeten:threetenbp to v1.6.7 ([#1551](https://github.com/googleapis/gapic-generator-java/issues/1551)) ([b11395d](https://github.com/googleapis/gapic-generator-java/commit/b11395dd113555e91897ca78f4eaf7277c870892))
* Update google api dependencies ([#1526](https://github.com/googleapis/gapic-generator-java/issues/1526)) ([750d4f5](https://github.com/googleapis/gapic-generator-java/commit/750d4f5e489ce517d9b9a09969038556c112ebeb))
* Update netty dependencies to v4.1.90.final ([#1490](https://github.com/googleapis/gapic-generator-java/issues/1490)) ([894ba37](https://github.com/googleapis/gapic-generator-java/commit/894ba379201fe1fa8afe80ad58d17b69554b82d5))


### Documentation

* Update steps to generate a library locally ([#1539](https://github.com/googleapis/gapic-generator-java/issues/1539)) ([0ed1359](https://github.com/googleapis/gapic-generator-java/commit/0ed1359f7e07a6d231e562ef6cc9426319286bee))

## [2.15.3](https://github.com/googleapis/gapic-generator-java/compare/v2.15.2...v2.15.3) (2023-03-14)


### Bug Fixes

* Allow custom HttpRules for REST LROs ([#1288](https://github.com/googleapis/gapic-generator-java/issues/1288)) ([f8ccd2a](https://github.com/googleapis/gapic-generator-java/commit/f8ccd2a1428f96f0ff3231247964c272144b8dc9))


### Dependencies

* Update actions/checkout action to v3 ([#1365](https://github.com/googleapis/gapic-generator-java/issues/1365)) ([84036c7](https://github.com/googleapis/gapic-generator-java/commit/84036c706620a6c7aef05aeaa5969e7dd0b4674b))
* Update dependency com.google.api.grpc:grpc-google-common-protos to v2.14.2 ([#1451](https://github.com/googleapis/gapic-generator-java/issues/1451)) ([9566040](https://github.com/googleapis/gapic-generator-java/commit/95660400209c114a460a7d4170da4f093e6e3526))
* Update dependency com.google.api.grpc:grpc-google-iam-v1 to v1.9.2 ([#1452](https://github.com/googleapis/gapic-generator-java/issues/1452)) ([b30359b](https://github.com/googleapis/gapic-generator-java/commit/b30359bd2a35c3f267b58748012d080bf3f1edf8))
* Update dependency com.google.code.gson:gson to v2.10.1 ([#1443](https://github.com/googleapis/gapic-generator-java/issues/1443)) ([0ccf457](https://github.com/googleapis/gapic-generator-java/commit/0ccf457a774bd3dbe3b101d82222bdc39f6e5b0e))
* Update dependency com.google.errorprone:error_prone_annotations to v2.18.0 ([#1459](https://github.com/googleapis/gapic-generator-java/issues/1459)) ([2671dcf](https://github.com/googleapis/gapic-generator-java/commit/2671dcf9131591acaa96d474412ce414fef54a6f))
* Update dependency io.grpc:grpc-core to v1.53.0 ([#1463](https://github.com/googleapis/gapic-generator-java/issues/1463)) ([6762df6](https://github.com/googleapis/gapic-generator-java/commit/6762df6e121382545cc6da1375553f3de79acb2d))
* Update dependency io.perfmark:perfmark-api to v0.26.0 ([#1465](https://github.com/googleapis/gapic-generator-java/issues/1465)) ([174f3d8](https://github.com/googleapis/gapic-generator-java/commit/174f3d82408e569028ba5c025b1bc9823e8cdb93))
* Update dependency net.bytebuddy:byte-buddy to v1.14.1 ([#1467](https://github.com/googleapis/gapic-generator-java/issues/1467)) ([c0d851a](https://github.com/googleapis/gapic-generator-java/commit/c0d851a2afde63b07a620771177c6eb2cf29dc25))
* Update dependency net.bytebuddy:byte-buddy to v1.14.2 ([#1481](https://github.com/googleapis/gapic-generator-java/issues/1481)) ([cbceed2](https://github.com/googleapis/gapic-generator-java/commit/cbceed21237a5dba62cc318ad0c1903a8d7768f3))
* Update dependency org.apache.commons:commons-lang3 to v3.12.0 ([#1468](https://github.com/googleapis/gapic-generator-java/issues/1468)) ([2506b91](https://github.com/googleapis/gapic-generator-java/commit/2506b91196f8cbf7f7297af15e01529e7ee51270))
* Update dependency org.graalvm.sdk:graal-sdk to v22.3.1 ([#1444](https://github.com/googleapis/gapic-generator-java/issues/1444)) ([edb5d12](https://github.com/googleapis/gapic-generator-java/commit/edb5d12689400d0a127b240fc68f12c00e7db0a8))
* Update dependency org.threeten:threetenbp to v1.6.5 ([#1446](https://github.com/googleapis/gapic-generator-java/issues/1446)) ([842b9e3](https://github.com/googleapis/gapic-generator-java/commit/842b9e398caa6ea273d790c0bc577e7ec22cad39))
* Update google api dependencies ([#1470](https://github.com/googleapis/gapic-generator-java/issues/1470)) ([e1667f1](https://github.com/googleapis/gapic-generator-java/commit/e1667f1a0eee9a3b9977eae6ee2ae27e06185ad9))
* Update google auth library dependencies to v1.16.0 ([#1471](https://github.com/googleapis/gapic-generator-java/issues/1471)) ([de67f4e](https://github.com/googleapis/gapic-generator-java/commit/de67f4e2c772ecb1b4a8f7a3d58c5bce564cf788))
* Update google http client dependencies to v1.43.0 ([#1472](https://github.com/googleapis/gapic-generator-java/issues/1472)) ([524eddb](https://github.com/googleapis/gapic-generator-java/commit/524eddb17488701d0217ee270cdba804b2f8d09b))
* Update google http client dependencies to v1.43.1 ([#1487](https://github.com/googleapis/gapic-generator-java/issues/1487)) ([da52fdd](https://github.com/googleapis/gapic-generator-java/commit/da52fdd34c6c1c9d329ff6d1834eee2311ed80c6))
* Update netty dependencies ([#1448](https://github.com/googleapis/gapic-generator-java/issues/1448)) ([97079ef](https://github.com/googleapis/gapic-generator-java/commit/97079ef6b0115d7ed14ffd6060d63b03c0cbd4fd))

## [2.15.2](https://github.com/googleapis/gapic-generator-java/compare/v2.15.1...v2.15.2) (2023-02-28)


### Bug Fixes

* Change the default scope of gax from implementation to api in auto-generated gradle files for self-service client libraries. ([#1374](https://github.com/googleapis/gapic-generator-java/issues/1374)) ([eee7573](https://github.com/googleapis/gapic-generator-java/commit/eee757347a93f75e767870c90814322b29ed5275))


### Dependencies

* Update dependency com.google.http-client:google-http-client-bom to v1.43.0 ([#1377](https://github.com/googleapis/gapic-generator-java/issues/1377)) ([df3e35f](https://github.com/googleapis/gapic-generator-java/commit/df3e35f862724b4465a886f716afa8dd0b23d27f))
* Update dependency gradle to v7.6.1 ([#1376](https://github.com/googleapis/gapic-generator-java/issues/1376)) ([5d61dcb](https://github.com/googleapis/gapic-generator-java/commit/5d61dcb1ef504ba0553c9ae33faab363b5650f4f))

## [2.15.1](https://github.com/googleapis/gapic-generator-java/compare/v2.15.0...v2.15.1) (2023-02-15)


### Bug Fixes

* **batcher:** Exceptions in unaryCaller bubble up ([#1166](https://github.com/googleapis/gapic-generator-java/issues/1166)) ([bcf5ed8](https://github.com/googleapis/gapic-generator-java/commit/bcf5ed856d664f84fa033cee0cc27fb57b97b678))
* **deps:** Update dependency com.google.auth:google-auth-library-bom to v1.16.0 ([#1355](https://github.com/googleapis/gapic-generator-java/issues/1355)) ([b7de1bc](https://github.com/googleapis/gapic-generator-java/commit/b7de1bc260a641e0c0ca717a33d61fbaa8a2fcf6))
* **deps:** Update dependency io.grpc:grpc-bom to v1.53.0 ([#1345](https://github.com/googleapis/gapic-generator-java/issues/1345)) ([1e82422](https://github.com/googleapis/gapic-generator-java/commit/1e824223afa0b8a62e9e50ae38cc73e8036e6348))
* Use pkg_tar from rules_pkg ([#1303](https://github.com/googleapis/gapic-generator-java/issues/1303)) ([fbae565](https://github.com/googleapis/gapic-generator-java/commit/fbae565291aee092acab3df86de858aea129b670))

## [2.15.0](https://github.com/googleapis/gapic-generator-java/compare/v2.14.0...v2.15.0) (2023-02-02)


### Features

* Do not generate Service REST code if there are no matching RPC in a Service ([#1236](https://github.com/googleapis/gapic-generator-java/issues/1236)) ([9c06bc9](https://github.com/googleapis/gapic-generator-java/commit/9c06bc95b9aac869bf21c343cbb4b857aa25b593))


### Bug Fixes

* **ast:** Update import generation to cover annotation parameters ([#1229](https://github.com/googleapis/gapic-generator-java/issues/1229)) ([bdf12b0](https://github.com/googleapis/gapic-generator-java/commit/bdf12b0df91446abac8cb1bf2d361bb886eb91a2))
* **deps:** Update dependency com.google.auth:google-auth-library-bom to v1.15.0 ([#1278](https://github.com/googleapis/gapic-generator-java/issues/1278)) ([f80861b](https://github.com/googleapis/gapic-generator-java/commit/f80861ba0518be2b9774b3235535f4bd3a27216a))
* **java:** Initialize netty-shaded at run-time and add reflection configurations for netty classes ([#1290](https://github.com/googleapis/gapic-generator-java/issues/1290)) ([b7ca95f](https://github.com/googleapis/gapic-generator-java/commit/b7ca95f12dfe8287c133e09534be1fc46882ce6c))
* Support mvn fmt:check and fmt:format ([#1266](https://github.com/googleapis/gapic-generator-java/issues/1266)) ([c96a2e7](https://github.com/googleapis/gapic-generator-java/commit/c96a2e7cf647cf245199c33ad989c8050208646e))

## [2.14.0](https://github.com/googleapis/gapic-generator-java/compare/v2.13.0...v2.14.0) (2023-01-19)


### Features

* Add callable getters for non-eligible or non-enabled REST methods ([#1211](https://github.com/googleapis/gapic-generator-java/issues/1211)) ([84a1355](https://github.com/googleapis/gapic-generator-java/commit/84a1355a6db4754404196f153958359f9ba55437))
* Add the google.rpc.context.AuditContext and google.rpc.http message to the open source ([#1248](https://github.com/googleapis/gapic-generator-java/issues/1248)) ([1538ad8](https://github.com/googleapis/gapic-generator-java/commit/1538ad8e88db7e03ed14337b83c0ddfae287538e))
* Update JavaDoc to use [@return](https://github.com/return) ([#1233](https://github.com/googleapis/gapic-generator-java/issues/1233)) ([d13d3c3](https://github.com/googleapis/gapic-generator-java/commit/d13d3c381bb64e1833cbc443a12781084e89494b))


### Bug Fixes

* Add native image reflect-config.json to gax-grpc ([#1251](https://github.com/googleapis/gapic-generator-java/issues/1251)) ([ff2d118](https://github.com/googleapis/gapic-generator-java/commit/ff2d118341dfc95f34bcf114b005625d2fc930f6))
* **deps:** Update dependency com.google.api:api-common to v2.4.0 ([#1228](https://github.com/googleapis/gapic-generator-java/issues/1228)) ([422315e](https://github.com/googleapis/gapic-generator-java/commit/422315ef0ee35740a2772eaa59cfc757ee499e28))
* **deps:** Update dependency com.google.code.gson:gson to v2.10.1 ([#1219](https://github.com/googleapis/gapic-generator-java/issues/1219)) ([5a3aed2](https://github.com/googleapis/gapic-generator-java/commit/5a3aed2a8f9a89657d5a8ea858b668ff6ab92b96))
* **deps:** Update dependency com.google.errorprone:error_prone_annotations to v2.18.0 ([#1231](https://github.com/googleapis/gapic-generator-java/issues/1231)) ([746cc9d](https://github.com/googleapis/gapic-generator-java/commit/746cc9d28809636afdecc85833a2122ec4fbeb76))
* **deps:** Update dependency com.google.errorprone:error_prone_annotations to v2.18.0 ([#1232](https://github.com/googleapis/gapic-generator-java/issues/1232)) ([467ea48](https://github.com/googleapis/gapic-generator-java/commit/467ea485a32bcb3e9c0f69274edfd66a595e70d8))
* **deps:** Update dependency io.grpc:grpc-bom to v1.52.1 ([#1240](https://github.com/googleapis/gapic-generator-java/issues/1240)) ([89e0fa2](https://github.com/googleapis/gapic-generator-java/commit/89e0fa2f4efdc48a5d6dbd59706beeae4793f3f3))
* **deps:** Update dependency io.grpc:grpc-protobuf to v1.52.1 ([#1241](https://github.com/googleapis/gapic-generator-java/issues/1241)) ([f30d96c](https://github.com/googleapis/gapic-generator-java/commit/f30d96c9b9333c48e2d0067af1324bc9447f3580))
* **deps:** Update dependency io.grpc:grpc-stub to v1.52.1 ([#1242](https://github.com/googleapis/gapic-generator-java/issues/1242)) ([44cef75](https://github.com/googleapis/gapic-generator-java/commit/44cef75afc94516632d1e7e04beffa2a2c7c56d0))
* **deps:** Update dependency org.graalvm.sdk:graal-sdk to v22.3.1 ([#1252](https://github.com/googleapis/gapic-generator-java/issues/1252)) ([328f4e1](https://github.com/googleapis/gapic-generator-java/commit/328f4e1fb82fb3e413725f0b5a27e05fc45a800d))
* Handle cancel in ReleasingClientCall and rethrow the exception in start ([#1221](https://github.com/googleapis/gapic-generator-java/issues/1221)) ([8a61249](https://github.com/googleapis/gapic-generator-java/commit/8a6124950ba51f79f0a42afd20f758e937002b35))
* Rename gapic-parent-pom to groupId to google.cloud.api ([#1238](https://github.com/googleapis/gapic-generator-java/issues/1238)) ([7e6e750](https://github.com/googleapis/gapic-generator-java/commit/7e6e75062eacad157c92c7c9063dcb1899a9953d))

## [2.13.0](https://github.com/googleapis/gapic-generator-java/compare/v2.12.0...v2.13.0) (2023-01-06)


### Features

* Parent pom and gapic-generator-java-bom ([#1170](https://github.com/googleapis/gapic-generator-java/issues/1170)) ([4dd5fcf](https://github.com/googleapis/gapic-generator-java/commit/4dd5fcf7b817c56bcfd90c3977bf003f212fbddf))
* Use gapic-generator-java jar in the client library generation process ([#918](https://github.com/googleapis/gapic-generator-java/issues/918)) ([0051f49](https://github.com/googleapis/gapic-generator-java/commit/0051f4939727ca818509332d78ba8fbbe98ea2c8))


### Bug Fixes

* **deps:** Update dependency com.google.auth:google-auth-library-oauth2-http to v1.14.0 ([#1183](https://github.com/googleapis/gapic-generator-java/issues/1183)) ([45fd2cb](https://github.com/googleapis/gapic-generator-java/commit/45fd2cbf4713ef070b7a78133169668541a14e74))
* **deps:** Update dependency com.google.auto.value:auto-value-annotations to v1.10.1 ([#1184](https://github.com/googleapis/gapic-generator-java/issues/1184)) ([e2e01a0](https://github.com/googleapis/gapic-generator-java/commit/e2e01a01b7b222f629ddb3b57fe2537a5d3906de))
* **deps:** Update dependency com.google.cloud:google-iam-policy to v1.6.22 ([#1145](https://github.com/googleapis/gapic-generator-java/issues/1145)) ([83f3aec](https://github.com/googleapis/gapic-generator-java/commit/83f3aec3d920bca422b12f91f007081d49d0c4cb))
* **deps:** Update dependency com.google.cloud:libraries-bom to v26.2.0 ([#1185](https://github.com/googleapis/gapic-generator-java/issues/1185)) ([83766f1](https://github.com/googleapis/gapic-generator-java/commit/83766f1c180b756e58bf8c147ea697fbe51a4d13))
* **deps:** Update dependency com.google.errorprone:error_prone_annotations to v2.17.0 - abandoned ([#1205](https://github.com/googleapis/gapic-generator-java/issues/1205)) ([031fff6](https://github.com/googleapis/gapic-generator-java/commit/031fff6f99694b6035c134fe96b7bef1a6143111))
* **deps:** Update dependency com.google.errorprone:error_prone_annotations to v2.17.0 ([#1202](https://github.com/googleapis/gapic-generator-java/issues/1202)) ([c9a35b9](https://github.com/googleapis/gapic-generator-java/commit/c9a35b9eb04e1db8b30a81547afb170947f630ec))
* **deps:** Update dependency com.google.guava:guava to v31.1-jre ([#1146](https://github.com/googleapis/gapic-generator-java/issues/1146)) ([e70f7f6](https://github.com/googleapis/gapic-generator-java/commit/e70f7f6d6e13ab6ddb2a91d378ddd2b286e8902b))
* **deps:** Update dependency com.google.protobuf:protobuf-bom to v3.21.12 ([#1116](https://github.com/googleapis/gapic-generator-java/issues/1116)) ([41a220c](https://github.com/googleapis/gapic-generator-java/commit/41a220c227195c159fd408b8df07df7f3e7332cd))
* **deps:** Update dependency com.google.protobuf:protobuf-java to v3.21.12 ([#1155](https://github.com/googleapis/gapic-generator-java/issues/1155)) ([ddb7edf](https://github.com/googleapis/gapic-generator-java/commit/ddb7edfa261540927db240363d3ec5715db2b3db))
* **deps:** Update dependency io.grpc:grpc-bom to v1.51.1 ([#1137](https://github.com/googleapis/gapic-generator-java/issues/1137)) ([582c796](https://github.com/googleapis/gapic-generator-java/commit/582c796ee9dacc3a127aef0821c40afd8eb537ef))
* **deps:** Update dependency io.grpc:grpc-bom to v1.51.1 ([#1157](https://github.com/googleapis/gapic-generator-java/issues/1157)) ([7528b45](https://github.com/googleapis/gapic-generator-java/commit/7528b45fb2e8a5046c931dc28619ef17143810ed))
* **deps:** Update dependency io.grpc:grpc-protobuf to v1.51.1 ([#1158](https://github.com/googleapis/gapic-generator-java/issues/1158)) ([14081a1](https://github.com/googleapis/gapic-generator-java/commit/14081a1048d030748abeb200b26e6ca9add7ba8e))
* **deps:** Update dependency io.grpc:grpc-stub to v1.51.1 ([#1159](https://github.com/googleapis/gapic-generator-java/issues/1159)) ([8745b57](https://github.com/googleapis/gapic-generator-java/commit/8745b570a033563a44e0d8f9763c2a03e2fa77b2))
* **deps:** Update dependency jacoco to v0.8.8 ([#1204](https://github.com/googleapis/gapic-generator-java/issues/1204)) ([8c5e17b](https://github.com/googleapis/gapic-generator-java/commit/8c5e17ba325b7711f9ba9501992ab48e736ffc18))
* **deps:** Update dependency org.mockito:mockito-core to v4.10.0 ([#1186](https://github.com/googleapis/gapic-generator-java/issues/1186)) ([1d791fb](https://github.com/googleapis/gapic-generator-java/commit/1d791fb932052b9e72ec5aabd0cb2fde36ed7a55))
* **deps:** Update dependency org.mockito:mockito-core to v4.11.0 ([#1200](https://github.com/googleapis/gapic-generator-java/issues/1200)) ([25c7b26](https://github.com/googleapis/gapic-generator-java/commit/25c7b266edf1d2e1332bab8fbfe28486cb237ed6))
* **deps:** Update dependency org.threeten:threetenbp to v1.6.5 ([#1160](https://github.com/googleapis/gapic-generator-java/issues/1160)) ([53be6b7](https://github.com/googleapis/gapic-generator-java/commit/53be6b7cfd0928675518cf5ba4784ce57c973993))
* Focus 'Running the Plugin' documentation on googleapis, not showcase ([#1196](https://github.com/googleapis/gapic-generator-java/issues/1196)) ([d58b2e5](https://github.com/googleapis/gapic-generator-java/commit/d58b2e53238d42477ac246eefe9166192618180a))


### Documentation

* Development with local gapic-generator-java ([#1132](https://github.com/googleapis/gapic-generator-java/issues/1132)) ([079d830](https://github.com/googleapis/gapic-generator-java/commit/079d830a234bb6923f3b71894f40fd0fc31649e6))
* Development with local gapic-generator-java ([#1132](https://github.com/googleapis/gapic-generator-java/issues/1132)) ([cbb9dce](https://github.com/googleapis/gapic-generator-java/commit/cbb9dce85438942f7b4f1b163756e311f54d75ea))

## [2.12.0](https://github.com/googleapis/gapic-generator-java/compare/v2.11.0...v2.12.0) (2022-12-09)


### Features

* Add scripts to release gapic-generator-java as jar ([#1111](https://github.com/googleapis/gapic-generator-java/issues/1111)) ([7941cc5](https://github.com/googleapis/gapic-generator-java/commit/7941cc5d7b3aca732d27b4f00eae18a145f8c459))


### Bug Fixes

* Sonar coverage for non-standard directory structure ([#1108](https://github.com/googleapis/gapic-generator-java/issues/1108)) ([60dc190](https://github.com/googleapis/gapic-generator-java/commit/60dc1904676b1ede343b84998658eca2e20cc56a))


### Dependencies

* Explicitly declare dependencies instead of using shared-dependencies-bom ([#1115](https://github.com/googleapis/gapic-generator-java/issues/1115)) ([402d565](https://github.com/googleapis/gapic-generator-java/commit/402d565e318b31199f3765b3bf82528fb189255d))

## [2.11.0](https://github.com/googleapis/gapic-generator-java/compare/v2.10.3...v2.11.0) (2022-11-30)


### Features

* **ast:** Array expressions ([#1080](https://github.com/googleapis/gapic-generator-java/issues/1080)) ([bd9532c](https://github.com/googleapis/gapic-generator-java/commit/bd9532c6663f2e8f9d9978cd0a3cffde821960ec))


### Bug Fixes

* **ast:** Add import generation for annotations on VariableExpr ([#1076](https://github.com/googleapis/gapic-generator-java/issues/1076)) ([f5d5524](https://github.com/googleapis/gapic-generator-java/commit/f5d5524e626f26ef53d4ae6c02118ef70ace205a))
* **deps:** Update dependency com.google.cloud:google-cloud-shared-dependencies to v3.0.6 ([#1088](https://github.com/googleapis/gapic-generator-java/issues/1088)) ([0e75338](https://github.com/googleapis/gapic-generator-java/commit/0e75338dcb083c4cccd35d8682ba9cbe4e314ef8))
* Support testing nested argument method signatures and 'double' field assertions ([#1094](https://github.com/googleapis/gapic-generator-java/issues/1094)) ([4bf419f](https://github.com/googleapis/gapic-generator-java/commit/4bf419f764e2ecfdac9caf03968f40a8cd564655))

## [2.10.3](https://github.com/googleapis/gapic-generator-java/compare/v2.10.2...v2.10.3) (2022-11-01)


### Bug Fixes

* Fix REST transport client creation generated javadoc sample ([#1077](https://github.com/googleapis/gapic-generator-java/issues/1077)) ([9ac0ca8](https://github.com/googleapis/gapic-generator-java/commit/9ac0ca80b09077f7880f0029b5d7b090a10a10b8))
* Fixes regionTag breakage ([#1068](https://github.com/googleapis/gapic-generator-java/issues/1068)) ([bed9f72](https://github.com/googleapis/gapic-generator-java/commit/bed9f721fbafb5f2e974b6f43f8455f2fdb581f7))

## [2.10.2](https://github.com/googleapis/gapic-generator-java/compare/v2.10.1...v2.10.2) (2022-10-24)


### Bug Fixes

* **deps:** Update dependency com.google.cloud:google-cloud-shared-dependencies to v3.0.5 ([#1063](https://github.com/googleapis/gapic-generator-java/issues/1063)) ([80ed62c](https://github.com/googleapis/gapic-generator-java/commit/80ed62cf6aff395d150e394a44fb60da37e9a6ae))
* Update regionTag to use service name ([#1047](https://github.com/googleapis/gapic-generator-java/issues/1047)) ([68b33c2](https://github.com/googleapis/gapic-generator-java/commit/68b33c23948ffe7a2bf6acd53731d786257f6bd1))

## [2.10.1](https://github.com/googleapis/gapic-generator-java/compare/v2.10.0...v2.10.1) (2022-10-20)


### Bug Fixes

* **deps:** Update dependency com.google.cloud:google-cloud-shared-dependencies to v3.0.2 ([#1035](https://github.com/googleapis/gapic-generator-java/issues/1035)) ([f76634a](https://github.com/googleapis/gapic-generator-java/commit/f76634af8497ef2977376af570d175eb0fe91e6f))
* **deps:** Update dependency com.google.cloud:google-cloud-shared-dependencies to v3.0.3 ([#1039](https://github.com/googleapis/gapic-generator-java/issues/1039)) ([9d25e47](https://github.com/googleapis/gapic-generator-java/commit/9d25e477126db35efd111a2b8ee76332b549ce91))
* **deps:** Update dependency com.google.cloud:google-cloud-shared-dependencies to v3.0.4 ([#1050](https://github.com/googleapis/gapic-generator-java/issues/1050)) ([3b052e2](https://github.com/googleapis/gapic-generator-java/commit/3b052e27bb704124ae3214dd3e8708f8e32afe6c))
* **deps:** Update dependency org.yaml:snakeyaml to v1.32 ([#1037](https://github.com/googleapis/gapic-generator-java/issues/1037)) ([b520aa0](https://github.com/googleapis/gapic-generator-java/commit/b520aa0d35407daf96123d63c3112b9f29715115))
* **deps:** Update dependency org.yaml:snakeyaml to v1.33 ([#1043](https://github.com/googleapis/gapic-generator-java/issues/1043)) ([67a5dc5](https://github.com/googleapis/gapic-generator-java/commit/67a5dc5e4dd3302ececd212dd047f5731c4bf74d))
* Get numeric value for Enum fields if it is configured as query param or path param ([#1042](https://github.com/googleapis/gapic-generator-java/issues/1042)) ([0fdfa67](https://github.com/googleapis/gapic-generator-java/commit/0fdfa67fec84e1983c12b10c7b7ae7a0efa80f4d))
* Update sample region tag to parse host instead of proto package ([#1040](https://github.com/googleapis/gapic-generator-java/issues/1040)) ([01c14d4](https://github.com/googleapis/gapic-generator-java/commit/01c14d41d4195963c0ed46f310360f39eec6d420))

## [2.10.0](https://github.com/googleapis/gapic-generator-java/compare/v2.9.0...v2.10.0) (2022-09-06)


### Features

* Add support for rest numeric enums. ([#1020](https://github.com/googleapis/gapic-generator-java/issues/1020)) ([0a89829](https://github.com/googleapis/gapic-generator-java/commit/0a898299a6adb28dec6ce2af762827a434242311))
* **ast:** Extend support for annotation named parameters ([#1012](https://github.com/googleapis/gapic-generator-java/issues/1012)) ([5d3ff75](https://github.com/googleapis/gapic-generator-java/commit/5d3ff75db429d6dd43b0c3fc5dd4bfcb5ad06ae4))
* Update autogenerated snippet disclaimer ([#1029](https://github.com/googleapis/gapic-generator-java/issues/1029)) ([16f26f6](https://github.com/googleapis/gapic-generator-java/commit/16f26f63aa61b4ac4d42a1548a868fc1dd49c0eb))


### Bug Fixes

* **ast:** Removed lambda void return type check ([#1019](https://github.com/googleapis/gapic-generator-java/issues/1019)) ([317eff6](https://github.com/googleapis/gapic-generator-java/commit/317eff6cc3f5dc46896465d47a0d5135ed8dd0d2))
* **bazel:** Do not emit empty rm commands ([#1027](https://github.com/googleapis/gapic-generator-java/issues/1027)) ([7064f4c](https://github.com/googleapis/gapic-generator-java/commit/7064f4c6095f47895ec86712b5a3c73a66a76e3c))
* **bazel:** Do not print current working directory ([#1028](https://github.com/googleapis/gapic-generator-java/issues/1028)) ([5d94f1d](https://github.com/googleapis/gapic-generator-java/commit/5d94f1dc0fad961774f884e6cb1dc210f925f57d))
* **deps:** Update dependency com.google.cloud ([27a8aa2](https://github.com/googleapis/gapic-generator-java/commit/27a8aa21766589c044a2f2db2b46e20e4321cf59))
* **deps:** Update dependency com.google.cloud ([883fafa](https://github.com/googleapis/gapic-generator-java/commit/883fafa409e74a3943f147e01c7a100e9336e4a9))
* **deps:** Update dependency org.yaml ([56fd1ea](https://github.com/googleapis/gapic-generator-java/commit/56fd1ea51c949fa87ced1a5680b5542953097914))

## [2.9.0](https://github.com/googleapis/gapic-generator-java/compare/v2.8.3...v2.9.0) (2022-07-11)


### Features

* **ast:** add support for LambdaExpr to infer type from return expr type ([#1011](https://github.com/googleapis/gapic-generator-java/issues/1011)) ([a179558](https://github.com/googleapis/gapic-generator-java/commit/a1795582948d6c721544b920c86a4642df3b39a0))

## [2.8.3](https://github.com/googleapis/gapic-generator-java/compare/v2.8.2...v2.8.3) (2022-06-27)


### Bug Fixes

* **deps:** update dependency com.google.cloud:google-cloud-shared-dependencies to v2.13.0 ([#1009](https://github.com/googleapis/gapic-generator-java/issues/1009)) ([9dcc470](https://github.com/googleapis/gapic-generator-java/commit/9dcc4705ba429ed63468033711b2043386ab5dd3))
* Fix mixin mock service and gradle build generation ([#1010](https://github.com/googleapis/gapic-generator-java/issues/1010)) ([d896e6e](https://github.com/googleapis/gapic-generator-java/commit/d896e6e1b052a9da5ef1304220ec88017a24991d))
* make `BetaApi` the `getHttpJsonOperationsClient()` in case of multitransport clients ([#1007](https://github.com/googleapis/gapic-generator-java/issues/1007)) ([badd554](https://github.com/googleapis/gapic-generator-java/commit/badd5544d1cd7e03e8c1b282aac4954acb1bb5f0))

## [2.8.2](https://github.com/googleapis/gapic-generator-java/compare/v2.8.1...v2.8.2) (2022-06-14)


### Bug Fixes

* Fix typo in generated comment ([#1006](https://github.com/googleapis/gapic-generator-java/issues/1006)) ([14e76e7](https://github.com/googleapis/gapic-generator-java/commit/14e76e73802ec05f2fe4a820ab10c2dbf7321c6d))
* Remove HttpMethod compile time dependency from genrated HttpJson stubs ([#1004](https://github.com/googleapis/gapic-generator-java/issues/1004)) ([e5988a0](https://github.com/googleapis/gapic-generator-java/commit/e5988a0e4900efd1943e942f445f1e86fdd6b05c))

## [2.8.1](https://github.com/googleapis/gapic-generator-java/compare/v2.8.0...v2.8.1) (2022-06-09)


### Bug Fixes

* More REST transport fixes ([#1003](https://github.com/googleapis/gapic-generator-java/issues/1003)) ([2bed7cf](https://github.com/googleapis/gapic-generator-java/commit/2bed7cf4f4168977ccb413125c6bcb43422061fd))
* Multiple REST transport related fixes ([#997](https://github.com/googleapis/gapic-generator-java/issues/997)) ([61e2e96](https://github.com/googleapis/gapic-generator-java/commit/61e2e9660fa62536163982f3241fcc479973e163))
* use correct paginated field name ([#1001](https://github.com/googleapis/gapic-generator-java/issues/1001)) ([7f186b5](https://github.com/googleapis/gapic-generator-java/commit/7f186b551f14cc14a503b552ef73c52d9e491628))

## [2.8.0](https://github.com/googleapis/gapic-generator-java/compare/v2.7.0...v2.8.0) (2022-05-21)


### Features

* Add support for additional_bindings ([#993](https://github.com/googleapis/gapic-generator-java/issues/993)) ([ce58c18](https://github.com/googleapis/gapic-generator-java/commit/ce58c181691d0be826634d1bd8373f07c1295156))


### Bug Fixes

* [REGAPIC] Fix repeated fields handling for query parameters ([#989](https://github.com/googleapis/gapic-generator-java/issues/989)) ([f7ceab9](https://github.com/googleapis/gapic-generator-java/commit/f7ceab9ee53b34b2114b77bb4bebb693ad6989a7))
* [REGAPIC] Fix snake_case nested resource names in generated unit tests ([#990](https://github.com/googleapis/gapic-generator-java/issues/990)) ([b8e3ae4](https://github.com/googleapis/gapic-generator-java/commit/b8e3ae4c0ebe98d757acb079987d96b281aec1f5))
* **deps:** update dependency com.google.cloud:google-cloud-shared-dependencies to v2.11.0 ([#995](https://github.com/googleapis/gapic-generator-java/issues/995)) ([df7e08e](https://github.com/googleapis/gapic-generator-java/commit/df7e08ebdf76b8dfb4b9961d3c8670f8e95c788c))
* **deps:** update dependency com.google.cloud:google-cloud-shared-dependencies to v2.12.0 ([#996](https://github.com/googleapis/gapic-generator-java/issues/996)) ([4673e52](https://github.com/googleapis/gapic-generator-java/commit/4673e5206b4535d2e691b5c0b8cffaa5b35c4ca1))
* Fix `BetaApi` annotaiton usage for REST transport and clean `BetaApi` for default stubs in all transports ([#987](https://github.com/googleapis/gapic-generator-java/issues/987)) ([d22b966](https://github.com/googleapis/gapic-generator-java/commit/d22b966703644ea150b9ceee6df39cde17cf8df4))

## [2.7.0](https://github.com/googleapis/gapic-generator-java/compare/v2.6.1...v2.7.0) (2022-04-29)


### Features

* Add add autogenerated javadoc sample for selecting REST transport over gRPC ([#983](https://github.com/googleapis/gapic-generator-java/issues/983)) ([051713d](https://github.com/googleapis/gapic-generator-java/commit/051713dec7706713900b8708e3a6b9ab0639438e))
* inline disclaimer comment, use Sample/RegionTag, collect GapicClass samples (pt 2) ([#970](https://github.com/googleapis/gapic-generator-java/issues/970)) ([f8b3616](https://github.com/googleapis/gapic-generator-java/commit/f8b36168372118d6ed639263bae192647912ed43))
* SampleComposer, Sample, Region Tag (pt1) ([#933](https://github.com/googleapis/gapic-generator-java/issues/933)) ([b7cf105](https://github.com/googleapis/gapic-generator-java/commit/b7cf105764bdc6c0a952e7b2705a454bbce3eadf))
* write samples to file (pt3) ([#980](https://github.com/googleapis/gapic-generator-java/issues/980)) ([04a6665](https://github.com/googleapis/gapic-generator-java/commit/04a66659f3724d125206b09ad6cf9544d064e8ae))


### Bug Fixes

* allow empty services and java keywords as a method names ([#985](https://github.com/googleapis/gapic-generator-java/issues/985)) ([e37893c](https://github.com/googleapis/gapic-generator-java/commit/e37893c37e333c947b75f55b1974045509d171d4))
* **deps:** update dependency com.google.cloud:google-cloud-shared-dependencies to v2.10.0 ([#982](https://github.com/googleapis/gapic-generator-java/issues/982)) ([9e863f8](https://github.com/googleapis/gapic-generator-java/commit/9e863f8b7b0407cb1a58aba5952f54b56d15167f))
* **deps:** update dependency com.google.cloud:google-cloud-shared-dependencies to v2.8.0 ([#959](https://github.com/googleapis/gapic-generator-java/issues/959)) ([0ca7a21](https://github.com/googleapis/gapic-generator-java/commit/0ca7a21df4fbae041e96b97a7235eff01195f429))
* **deps:** update dependency com.google.cloud:google-cloud-shared-dependencies to v2.9.0 ([#973](https://github.com/googleapis/gapic-generator-java/issues/973)) ([8fa9f07](https://github.com/googleapis/gapic-generator-java/commit/8fa9f07123e5b2837ce2cd7fc6cf8bbed43ae2ae))
* **deps:** update dependency junit:junit to v4.13.2 ([#940](https://github.com/googleapis/gapic-generator-java/issues/940)) ([09c69f7](https://github.com/googleapis/gapic-generator-java/commit/09c69f723fd868d767b88467ed3c496461876c75))
* **deps:** update dependency org.yaml:snakeyaml to v1.30 ([#946](https://github.com/googleapis/gapic-generator-java/issues/946)) ([996c8c3](https://github.com/googleapis/gapic-generator-java/commit/996c8c38bc7d9e5730bdaf99fdd5b20aee5cd9b8))
* setups for manually runs single JUnit test ([7ab9ae3](https://github.com/googleapis/gapic-generator-java/commit/7ab9ae30d71e755877061596bfba8f6c87e0f66b))
* Update copyright to 2022 for newly generated files. ([#951](https://github.com/googleapis/gapic-generator-java/issues/951)) ([a6ad7d9](https://github.com/googleapis/gapic-generator-java/commit/a6ad7d9988fc9e42b371082130e3b7f6bab75719))

### [2.6.1](https://github.com/googleapis/gapic-generator-java/compare/v2.6.0...v2.6.1) (2022-02-11)


### Bug Fixes

* Routing annotation was not recognized on parsing protos ([#928](https://github.com/googleapis/gapic-generator-java/issues/928)) ([2ce9f42](https://github.com/googleapis/gapic-generator-java/commit/2ce9f42ce23e91ff4f559d6a44be57df9f97eb46))

## [2.6.0](https://github.com/googleapis/gapic-generator-java/compare/v2.5.0...v2.6.0) (2022-01-28)


### Features

* Support explicit dynamic routing header ([#887](https://github.com/googleapis/gapic-generator-java/issues/887)) ([bcc1bdb](https://github.com/googleapis/gapic-generator-java/commit/bcc1bdb5fdf57d8843a03318d746df7d8392d848))


### Bug Fixes

* increase default memory for Gradle builds on generated libraries ([#907](https://github.com/googleapis/gapic-generator-java/issues/907)) ([d210aa1](https://github.com/googleapis/gapic-generator-java/commit/d210aa193d45ba5fbdea6ae5f31e471401c27cb7))

## [2.5.0](https://github.com/googleapis/gapic-generator-java/compare/v2.4.1...v2.5.0) (2022-01-22)


### Features

* add server streaming support for REST transport ([#902](https://github.com/googleapis/gapic-generator-java/issues/902)) ([3b2dec6](https://github.com/googleapis/gapic-generator-java/commit/3b2dec6adf46823eeb6859e93362e63f00d84ffc))
* make generated test values comply with url path template ([#903](https://github.com/googleapis/gapic-generator-java/issues/903)) ([bfb35cd](https://github.com/googleapis/gapic-generator-java/commit/bfb35cd31c05b5fbd2ea8073bcdcfdd3496bca12))

### [2.4.1](https://github.com/googleapis/gapic-generator-java/compare/v2.4.0...v2.4.1) (2022-01-14)


### Bug Fixes

* expose extra deps transitively to library users ([#899](https://github.com/googleapis/gapic-generator-java/issues/899)) ([8fa2660](https://github.com/googleapis/gapic-generator-java/commit/8fa2660022cf65ebed011165378683173989332b))
* fix broken Gradle allJars task on generated libs ([#901](https://github.com/googleapis/gapic-generator-java/issues/901)) ([e990105](https://github.com/googleapis/gapic-generator-java/commit/e99010596d85f891a738de71be3b5fb9c857594a))

## [2.4.0](https://github.com/googleapis/gapic-generator-java/compare/v2.3.1...v2.4.0) (2022-01-11)


### Features

* add `install` "alias" for local publication ([#897](https://github.com/googleapis/gapic-generator-java/issues/897)) ([662d1ba](https://github.com/googleapis/gapic-generator-java/commit/662d1ba2dab831a7927159b85c196caf28f13cc7))

### [2.3.1](https://www.github.com/googleapis/gapic-generator-java/compare/v2.3.0...v2.3.1) (2022-01-10)


### Bug Fixes

* fix Gradle build error for sourcesJar task on generated libs ([#894](https://www.github.com/googleapis/gapic-generator-java/issues/894)) ([1060a1a](https://www.github.com/googleapis/gapic-generator-java/commit/1060a1af874d9be3f89c567635630f5ad6f8e830))

## [2.3.0](https://www.github.com/googleapis/gapic-generator-java/compare/v2.2.4...v2.3.0) (2022-01-06)


### Features

* support Java 9+ compilation of generated libs ([#889](https://www.github.com/googleapis/gapic-generator-java/issues/889)) ([83552c4](https://www.github.com/googleapis/gapic-generator-java/commit/83552c45611c5a79533d6872bf8824cc686e480d))

### [2.2.4](https://www.github.com/googleapis/gapic-generator-java/compare/v2.2.3...v2.2.4) (2022-01-04)


### Bug Fixes

* add missing dependencies in Gradle for "self-service" library generation ([#884](https://www.github.com/googleapis/gapic-generator-java/issues/884)) ([3adac5e](https://www.github.com/googleapis/gapic-generator-java/commit/3adac5e2898e40a1e5395ab65db037c26a4f7e1b))

### [2.2.3](https://www.github.com/googleapis/gapic-generator-java/compare/v2.2.2...v2.2.3) (2021-12-06)


### Bug Fixes

* **deps:** upgrade gax to 2.7.1, protobuf to 3.19.1, grpc to 1.42.1, and truth to 1.1.2, upgrade Bazel in GitHub Actions, and fix build error ([#877](https://www.github.com/googleapis/gapic-generator-java/issues/877)) ([1597707](https://www.github.com/googleapis/gapic-generator-java/commit/1597707e8d525f8abaa058bc0fdcbd3a825032c9))

### [2.2.2](https://www.github.com/googleapis/gapic-generator-java/compare/v2.2.1...v2.2.2) (2021-11-05)


### Bug Fixes

* **resnames:** ensure determinstic code generation ([#865](https://www.github.com/googleapis/gapic-generator-java/issues/865)) ([680874d](https://www.github.com/googleapis/gapic-generator-java/commit/680874dcf84a5c31abc5948fa59f2cb38f14d80c))
* revert gradle version used for generated assembly packages (bazel rules) ([#872](https://www.github.com/googleapis/gapic-generator-java/issues/872)) ([4e73c9a](https://www.github.com/googleapis/gapic-generator-java/commit/4e73c9aefa3527a0b28c0d32255accf679218b81))
* Use parent type instead of child_type in method doc sample ([#862](https://www.github.com/googleapis/gapic-generator-java/issues/862)) ([6a39c7f](https://www.github.com/googleapis/gapic-generator-java/commit/6a39c7f54180154f393e88196c79cc9ebba38e52))

### [2.2.1](https://www.github.com/googleapis/gapic-generator-java/compare/v2.2.0...v2.2.1) (2021-10-25)


### Bug Fixes

* gax batching regression ([#863](https://www.github.com/googleapis/gapic-generator-java/issues/863)) ([3a6f168](https://www.github.com/googleapis/gapic-generator-java/commit/3a6f168322962d1b54ed37ff258e228e4b2f33ed))

## [2.2.0](https://www.github.com/googleapis/gapic-generator-java/compare/v2.1.0...v2.2.0) (2021-10-09)


### Features

* Add REST AIP-151 LRO suport ([cb1b534](https://www.github.com/googleapis/gapic-generator-java/commit/cb1b534701b95781e5195e57eacf6d0abff252bf))
* enable self signed JWT for http ([#850](https://www.github.com/googleapis/gapic-generator-java/issues/850)) ([aba0ec0](https://www.github.com/googleapis/gapic-generator-java/commit/aba0ec00dc912400e1c0457d971d8f4120c5d03a))
* Implement DIREGAPIC LRO annotations ([#832](https://www.github.com/googleapis/gapic-generator-java/issues/832)) ([d7b29e0](https://www.github.com/googleapis/gapic-generator-java/commit/d7b29e05d7cfda1eb1a3b09d7ad7d625cab4bde1))
* REGAPIC initial implementation ([#824](https://www.github.com/googleapis/gapic-generator-java/issues/824)) ([fdcfe70](https://www.github.com/googleapis/gapic-generator-java/commit/fdcfe705b5fb42e11dfd511ccf7fdee40bcba131))
* REGAPIC Multitransport implementation (grpc+rest) ([#833](https://www.github.com/googleapis/gapic-generator-java/issues/833)) ([445daf4](https://www.github.com/googleapis/gapic-generator-java/commit/445daf472b337520f108b485abfdbe8b20b16b01))


### Bug Fixes

* [bazel] fix rest transport handling in assembly rule ([#835](https://www.github.com/googleapis/gapic-generator-java/issues/835)) ([92f7c1c](https://www.github.com/googleapis/gapic-generator-java/commit/92f7c1cf7b343947a64943fd7ee7ffb4d67a9d5a))
* DIREGAPIC LRO generated tests logic ([#838](https://www.github.com/googleapis/gapic-generator-java/issues/838)) ([8ae8e6f](https://www.github.com/googleapis/gapic-generator-java/commit/8ae8e6f111d0f3849986cd0445d6003601ced148))
* fix diregapic-lro logic ([#834](https://www.github.com/googleapis/gapic-generator-java/issues/834)) ([957f69a](https://www.github.com/googleapis/gapic-generator-java/commit/957f69a0dc063e77b5e49da28f0a6d9a4a6c3bdb))

## [2.1.0](https://www.github.com/googleapis/gapic-generator-java/compare/v2.0.1...v2.1.0) (2021-08-17)


### Features

* enable self signed jwt for gapic clients ([#794](https://www.github.com/googleapis/gapic-generator-java/issues/794)) ([1b7ee1e](https://www.github.com/googleapis/gapic-generator-java/commit/1b7ee1e3911e1c8ecab9a94d68d7a59b437d2449))

### [2.0.1](https://www.github.com/googleapis/gapic-generator-java/compare/v2.0.0...v2.0.1) (2021-08-06)


### Bug Fixes

* bring back unused resnames for Ads ([#821](https://www.github.com/googleapis/gapic-generator-java/issues/821)) ([7fa135c](https://www.github.com/googleapis/gapic-generator-java/commit/7fa135c9aa60c9cb15880e098d36ea81b95ea488))
* **resnames:** ensure deterministic resname order for samplegen ([#813](https://www.github.com/googleapis/gapic-generator-java/issues/813)) ([c4709df](https://www.github.com/googleapis/gapic-generator-java/commit/c4709df5d8ed9f0074f7e25df5db93e9d9b35749))

## [2.0.0](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.17...v2.0.0) (2021-08-03)


### ⚠ BREAKING CHANGES

* remove Exception, unused resname classes for Java major version bump (#814)

### Features

* Lambda-ize single-method anon classes (Java 8+) ([#815](https://www.github.com/googleapis/gapic-generator-java/issues/815)) ([19b661c](https://www.github.com/googleapis/gapic-generator-java/commit/19b661cc78757e37ce93bae96eb81cb4ac32658b))
* remove Exception, unused resname classes for Java major version bump ([#814](https://www.github.com/googleapis/gapic-generator-java/issues/814)) ([8abece2](https://www.github.com/googleapis/gapic-generator-java/commit/8abece20dd20e9ac8134df04301ce5b6e2ab76ad))

### [1.0.17](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.16...v1.0.17) (2021-08-02)


### Features

* **ast:** Add support for multi-catch blocks [ggj] ([#811](https://www.github.com/googleapis/gapic-generator-java/issues/811)) ([55ef1a6](https://www.github.com/googleapis/gapic-generator-java/commit/55ef1a63427683538e48050333eedb0666635568))
* **ast:** Add support for Throwable causes [ggj] ([#801](https://www.github.com/googleapis/gapic-generator-java/issues/801)) ([7fdeece](https://www.github.com/googleapis/gapic-generator-java/commit/7fdeeced7642fefdfdc5c6e898661c72fe9d78a8))
* **ast:** support throwing all kinds of expressions ([#810](https://www.github.com/googleapis/gapic-generator-java/issues/810)) ([0817650](https://www.github.com/googleapis/gapic-generator-java/commit/0817650b35487f32d7987ba718ce71fc7551e3a0))


### Bug Fixes

* (rest transport) Add `@BetaApi` to the generated `TransportServiceFactory` class and lro-specific method ([#787](https://www.github.com/googleapis/gapic-generator-java/issues/787)) ([ebe1aef](https://www.github.com/googleapis/gapic-generator-java/commit/ebe1aefbe860a41aabd8ced5268ccc6c7efdf791))
* prevent hanging by call backgroundResources.close() on stub.close() [ggj] ([#804](https://www.github.com/googleapis/gapic-generator-java/issues/804)) ([428db97](https://www.github.com/googleapis/gapic-generator-java/commit/428db97c1534255a60530a8ed6137efc17ed56f4))
* **resnames:** fallback to fully-qualified Object name upon proto typing conflicts [ggj] ([#803](https://www.github.com/googleapis/gapic-generator-java/issues/803)) ([e654bfb](https://www.github.com/googleapis/gapic-generator-java/commit/e654bfb936b571af2546f550c9a1589f8ad63d67))



### [1.0.16](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.15...v1.0.16) (2021-06-30)


### Bug Fixes

* **bazel:** Eradicate monolith deps from Java µgen repo ([#778](https://www.github.com/googleapis/gapic-generator-java/issues/778)) ([86f2472](https://www.github.com/googleapis/gapic-generator-java/commit/86f2472963f020127ef6ff92be6241d12e2273af))
* **build:** Update googleapis-discovery hash to fix compute integration test ([#782](https://www.github.com/googleapis/gapic-generator-java/issues/782)) ([46bb19a](https://www.github.com/googleapis/gapic-generator-java/commit/46bb19a7ee61da86b4be6d87a71f3cd210e753d2))
* **protoc:** Mirror protoc's field name conflict resolution logic in client generation ([#781](https://www.github.com/googleapis/gapic-generator-java/issues/781)) ([9432979](https://www.github.com/googleapis/gapic-generator-java/commit/9432979bab59f48c8645fa47d752cdd470d4a682))

### [1.0.15](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.14...v1.0.15) (2021-06-22)


### Features

* Implement field presence support for DIREGAPIC ([#774](https://www.github.com/googleapis/gapic-generator-java/issues/774)) ([c820361](https://www.github.com/googleapis/gapic-generator-java/commit/c82036105d299b0a1192cd0def5e68253e4f542c))


### Bug Fixes

* **service.yaml:** Remove allowlist restriction ([#776](https://www.github.com/googleapis/gapic-generator-java/issues/776)) ([8f42efd](https://www.github.com/googleapis/gapic-generator-java/commit/8f42efdb92d606a768a524517fe949c4f9112025))


### Miscellaneous Chores

* release 1.0.15 ([f752478](https://www.github.com/googleapis/gapic-generator-java/commit/f75247845344540a94c4efcd416f34f96ea0c2a3))

### [1.0.14](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.13...v1.0.14) (2021-06-17)


### Features

* Add DIREGAPIC-specific pagination ([#767](https://www.github.com/googleapis/gapic-generator-java/issues/767)) ([1294c29](https://www.github.com/googleapis/gapic-generator-java/commit/1294c298f50cc4474ae562e6a07f37a5f94fe5b8))


### Bug Fixes

* **bazel:** Remove monolith rule deps from the Java µgen Bazel rules ([#764](https://www.github.com/googleapis/gapic-generator-java/issues/764)) ([bff3efc](https://www.github.com/googleapis/gapic-generator-java/commit/bff3efc25e43692ea5b6e769c20d25d5b9a1e3d2))



### [1.0.13](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.12...v1.0.13) (2021-06-16)


### Bug Fixes

* **resnames:** Fix resname builder name conflicts in ctor with this assignment ([#769](https://www.github.com/googleapis/gapic-generator-java/issues/769)) ([edac844](https://www.github.com/googleapis/gapic-generator-java/commit/edac8447d74c43ab0db963a37f66e1029ab19f0c))

### [1.0.12](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.11...v1.0.12) (2021-06-10)


### Features

* add mtls support ([#672](https://www.github.com/googleapis/gapic-generator-java/issues/672)) ([1e24893](https://www.github.com/googleapis/gapic-generator-java/commit/1e24893a65daf8ef067e331364c591ac973b5e02))


### Bug Fixes

* **mocks:** Use java.lang.Object if there are protos named 'Object' ([#760](https://www.github.com/googleapis/gapic-generator-java/issues/760)) ([2a7064b](https://www.github.com/googleapis/gapic-generator-java/commit/2a7064b88fe26586bd8aed43b7a7d28c7e974ec0))
* **resnames:** Use anon resname classes when only wildcards are present ([#763](https://www.github.com/googleapis/gapic-generator-java/issues/763)) ([f0ecead](https://www.github.com/googleapis/gapic-generator-java/commit/f0ecead9f1cc645cdbb7f61cdfc820c7df95355d))


### Miscellaneous Chores

* release 1.0.12 ([02eab0e](https://www.github.com/googleapis/gapic-generator-java/commit/02eab0ec61260048a2684119cfd4fa2172f3a637))

### [1.0.11](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.10...v1.0.11) (2021-06-07)


### Features

* DIREGAPIC initial implementation ([#746](https://www.github.com/googleapis/gapic-generator-java/issues/746)) ([81f6737](https://www.github.com/googleapis/gapic-generator-java/commit/81f6737359ac6ce5ee2b42ab4f755fbb05a3cf28))


### Bug Fixes

* **bazel:** ensure integration tests diff files recursively ([#745](https://www.github.com/googleapis/gapic-generator-java/issues/745)) ([ddc75f9](https://www.github.com/googleapis/gapic-generator-java/commit/ddc75f9f3b84d0ea50638a79a63d40cf551211e2))
* **resnames:** filter out mixin services from resname pkg candidates ([#751](https://www.github.com/googleapis/gapic-generator-java/issues/751)) ([72fa76f](https://www.github.com/googleapis/gapic-generator-java/commit/72fa76f27379a74a143b1735f60ae3e00da4c1a6))
* **tests:** Ensure deterministic field ordering in test classes ([#743](https://www.github.com/googleapis/gapic-generator-java/issues/743)) ([fdb705b](https://www.github.com/googleapis/gapic-generator-java/commit/fdb705b0a39443fb0b7679d879f27e0aa1c36b67))
* **tests:** handle Java 11 set ordering differences for RPCs and fields in test/mock classes ([#750](https://www.github.com/googleapis/gapic-generator-java/issues/750)) ([eaf4592](https://www.github.com/googleapis/gapic-generator-java/commit/eaf4592e139fbc42810e7f60dc9967320195bf85))


### [1.0.10](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.9...v1.0.10) (2021-05-26)


### Miscellaneous Chores

* Pre-DIREGAPIC refactoring ([04c2faa](https://github.com/googleapis/gapic-generator-java/commit/04c2faa191a9b5a10b92392fe8482279c4404803))

### [1.0.9](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.8...v1.0.9) (2021-05-26)


### Features

* add AST support for lambdas ([#736](https://www.github.com/googleapis/gapic-generator-java/issues/736)) ([9ced678](https://www.github.com/googleapis/gapic-generator-java/commit/9ced6780c7a6b9934dd548002602618566a539d6))


### Bug Fixes

* Support enums in proto HTTP annotations ([#733](https://www.github.com/googleapis/gapic-generator-java/issues/733)) ([f5c7b86](https://www.github.com/googleapis/gapic-generator-java/commit/f5c7b86b43ad71ffe47d8ba039155db601638e1f))
* Support PubSub's _deleted-topic_ pattern ([#739](https://www.github.com/googleapis/gapic-generator-java/issues/739)) ([7d8c62d](https://www.github.com/googleapis/gapic-generator-java/commit/7d8c62d8e8922a3589e631299ecb0287bc41ba2d))


### [1.0.8](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.7...v1.0.8) (2021-05-24)


### Miscellaneous Chores

* DIREGAPIC refactoring ([ae17e7e](https://github.com/googleapis/gapic-generator-java/commit/ae17e7e9a272b422176962d896e387496b1806e8))

### [1.0.7](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.6...v1.0.7) (2021-05-21)


### Bug Fixes

* Add PubSub to service.yaml / mixin allowlist ([#729](https://www.github.com/googleapis/gapic-generator-java/issues/729)) ([e7f6d33](https://www.github.com/googleapis/gapic-generator-java/commit/e7f6d33051e335504b05c402d3b98c387a9f0daf))


### [1.0.6](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.5...v1.0.6) (2021-05-19)


### Bug Fixes

* **mixins:** Gate mixin RPC on HTTP rules, add yaml doc/http overrides ([#727](https://www.github.com/googleapis/gapic-generator-java/issues/727)) ([229da5d](https://www.github.com/googleapis/gapic-generator-java/commit/229da5d94cf7db060abf3ea006a20d1ade804597))

### [1.0.5](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.4...v1.0.5) (2021-05-17)


### Bug Fixes

* **types:** Reorder nested types for legacy protos w/ java_outer_class in one file ([#724](https://www.github.com/googleapis/gapic-generator-java/issues/724)) ([925356d](https://www.github.com/googleapis/gapic-generator-java/commit/925356d659aed4b8550ce526f1772a706661c246))

### [1.0.4](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.3...v1.0.4) (2021-05-13)


### Bug Fixes

* **types:** Use fully-qualified message type names ([#723](https://www.github.com/googleapis/gapic-generator-java/issues/723)) ([8a5c36c](https://www.github.com/googleapis/gapic-generator-java/commit/8a5c36ccce7540940ec7a4bf8751971c3741d89d))
* Use the right composers in deprecation unit tests ([#715](https://www.github.com/googleapis/gapic-generator-java/issues/715)) ([2318136](https://www.github.com/googleapis/gapic-generator-java/commit/2318136e49060c3212abfd6337e2c3ceb2c2fc69))

### [1.0.3](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.2...v1.0.3) (2021-04-07)


### Bug Fixes

* Comment typo in Parser.java ([#701](https://www.github.com/googleapis/gapic-generator-java/issues/701)) ([685feff](https://www.github.com/googleapis/gapic-generator-java/commit/685feff32bf16484895ed86121282360b3e2dab3))

### [1.0.2](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.1...v1.0.2) (2021-03-14)


### Bug Fixes

* add request with default value in regular paged callable method ([#690](https://www.github.com/googleapis/gapic-generator-java/issues/690)) ([cc8ce77](https://www.github.com/googleapis/gapic-generator-java/commit/cc8ce778f07577eb3ed4cc9d07ac25511b0f1acd))
* **mixins:** handle unit tests for mixin pagination methods ([#691](https://www.github.com/googleapis/gapic-generator-java/issues/691)) ([edd7443](https://www.github.com/googleapis/gapic-generator-java/commit/edd7443d16e5c9389de16e6235fe884f9b996cf6))
* **sample code:** Update client samples in  KMS goldens ([#697](https://www.github.com/googleapis/gapic-generator-java/issues/697)) ([3777196](https://www.github.com/googleapis/gapic-generator-java/commit/3777196f0225a8b28203f1c225a67ea56b8728d3))

### [1.0.1](https://www.github.com/googleapis/gapic-generator-java/compare/v1.0.0...v1.0.1) (2021-03-03)


### Bug Fixes

* **codegen:** Bump license year in generated file headers ([#685](https://www.github.com/googleapis/gapic-generator-java/issues/685)) ([d1b3032](https://www.github.com/googleapis/gapic-generator-java/commit/d1b3032d54bc75e2f4e1b954f7215cff1069110e))
* **metadata:** gate metadata file-gen on a CLI flag ([#684](https://www.github.com/googleapis/gapic-generator-java/issues/684)) ([738bf8a](https://www.github.com/googleapis/gapic-generator-java/commit/738bf8a95125cbdd33cb0f762afb415844bf9426))
* **mixins:** enable codegen for standalone mixin APIs, add IAM integ test ([#679](https://www.github.com/googleapis/gapic-generator-java/issues/679)) ([bbde184](https://www.github.com/googleapis/gapic-generator-java/commit/bbde184b4d5a6085a6f18fd8120bd79207f67c5d))
* **mixins:** enable RPC overrides to clobber mixed-in RPCs ([#678](https://www.github.com/googleapis/gapic-generator-java/issues/678)) ([0645de4](https://www.github.com/googleapis/gapic-generator-java/commit/0645de476d131be20839f74ba83b53483d0d0b6d))

## [1.0.0](https://www.github.com/googleapis/gapic-generator-java/compare/v0.0.21...v1.0.0) (2021-03-01)


### Bug Fixes

* add common srcs to test, check test targets only in codecov ([#669](https://www.github.com/googleapis/gapic-generator-java/issues/669)) ([f81ed0b](https://www.github.com/googleapis/gapic-generator-java/commit/f81ed0bdede477c51cd6755b5050933319a442c9))
* **release:** update gax-java to 1.62.0 ([#675](https://www.github.com/googleapis/gapic-generator-java/issues/675)) ([51ed181](https://www.github.com/googleapis/gapic-generator-java/commit/51ed181f9fa2747604054214db79e1f2398b1dac))
