# Changelog

### [1.93.5](https://www.github.com/googleapis/java-core/compare/v1.93.4...v1.93.5) (2020-05-26)


### Dependencies

* rely on shared-config for auto-value configuration ([#222](https://www.github.com/googleapis/java-core/issues/222)) ([22adbcf](https://www.github.com/googleapis/java-core/commit/22adbcf307cf5ef4819bad9afa91c4c83a9f31bb))
* update dependency com.google.api.grpc:proto-google-common-protos to v1.18.0 ([#215](https://www.github.com/googleapis/java-core/issues/215)) ([488a351](https://www.github.com/googleapis/java-core/commit/488a351ab41adafb1d969740bfbd3a6d2ddfd36d))
* update dependency com.google.guava:guava-bom to v29 ([#205](https://www.github.com/googleapis/java-core/issues/205)) ([48d3f97](https://www.github.com/googleapis/java-core/commit/48d3f970bd931ee5f04205ab939c318c69cbcf3f))
* update dependency com.google.http-client:google-http-client-bom to v1.35.0 ([#211](https://www.github.com/googleapis/java-core/issues/211)) ([e56b3ef](https://www.github.com/googleapis/java-core/commit/e56b3ef0e2ea5c96b36f8aae851fd26efe1337c1))
* update dependency com.google.protobuf:protobuf-bom to v3.12.0 ([#218](https://www.github.com/googleapis/java-core/issues/218)) ([b2c6f15](https://www.github.com/googleapis/java-core/commit/b2c6f15ae63302dbeea16ecb7d7b89404907516f))
* update dependency io.grpc:grpc-bom to v1.29.0 ([#209](https://www.github.com/googleapis/java-core/issues/209)) ([e63cb7c](https://www.github.com/googleapis/java-core/commit/e63cb7ca69e49afae50af1f2cf4fe3601984be49))
* update dependency org.threeten:threetenbp to v1.4.4 ([#210](https://www.github.com/googleapis/java-core/issues/210)) ([a837fa4](https://www.github.com/googleapis/java-core/commit/a837fa441fbbf83494709b1779e0ef35ef13b345))

### [1.93.4](https://www.github.com/googleapis/java-core/compare/v1.93.3...v1.93.4) (2020-04-06)


### Dependencies

* update core dependencies ([#198](https://www.github.com/googleapis/java-core/issues/198)) ([129b3f8](https://www.github.com/googleapis/java-core/commit/129b3f8b03e6809bcae9545a6c2484aa8acc2447))
* update dependency com.google.api:api-common to v1.9.0 ([#195](https://www.github.com/googleapis/java-core/issues/195)) ([ac19258](https://www.github.com/googleapis/java-core/commit/ac192586f086b91f479e85e4677ac2d10f10968a))
* update dependency com.google.api:gax-bom to v1.56.0 ([#201](https://www.github.com/googleapis/java-core/issues/201)) ([875c4ed](https://www.github.com/googleapis/java-core/commit/875c4ed001193dfe2c301ad7f163395a568bcb10))
* update dependency org.threeten:threetenbp to v1.4.3 ([#192](https://www.github.com/googleapis/java-core/issues/192)) ([ce6ec17](https://www.github.com/googleapis/java-core/commit/ce6ec179e4024264c0aee09f85f6f60218d46138))

### [1.93.3](https://www.github.com/googleapis/java-core/compare/v1.93.2...v1.93.3) (2020-03-16)


### Bug Fixes

* fix Timestamp.of(java.sql.Timestamp) pre-epoch on exact second ([#179](https://www.github.com/googleapis/java-core/issues/179)) ([9bfb54c](https://www.github.com/googleapis/java-core/commit/9bfb54c5a88c906bebcf90f81ed19aeece09befd))
* retry SSLException ([#183](https://www.github.com/googleapis/java-core/issues/183)) ([775a848](https://www.github.com/googleapis/java-core/commit/775a84877ef69d22ca7a4392edd0d3680df2256e))


### Dependencies

* revert gRPC updates ([#181](https://www.github.com/googleapis/java-core/issues/181)) ([f5ba782](https://www.github.com/googleapis/java-core/commit/f5ba782fe7078bd2e02d27d8770ad20a459c73f3))
* update dependency io.grpc:grpc-bom to v1.28.0 ([#178](https://www.github.com/googleapis/java-core/issues/178)) ([6d5632c](https://www.github.com/googleapis/java-core/commit/6d5632c22507d1d3d36a90778291a6fa25b4788b))

### [1.93.2](https://www.github.com/googleapis/java-core/compare/v1.93.1...v1.93.2) (2020-03-10)


### Bug Fixes

* verify correctness of map -> list equality ([#174](https://www.github.com/googleapis/java-core/issues/174)) ([f45d913](https://www.github.com/googleapis/java-core/commit/f45d9131d5d2bbb2cc4cec67ebe82054fda58f55))

### [1.93.1](https://www.github.com/googleapis/java-core/compare/v1.93.0...v1.93.1) (2020-02-28)


### Dependencies

* update dependency com.google.api-client:google-api-client-bom to v1.30.9 ([#171](https://www.github.com/googleapis/java-core/issues/171)) ([1fdcd5e](https://www.github.com/googleapis/java-core/commit/1fdcd5e839049f650ba7ebb003dea62511c8667f))

## [1.93.0](https://www.github.com/googleapis/java-core/compare/v1.92.6...v1.93.0) (2020-02-27)


### Features

* support conditional policies ([#110](https://www.github.com/googleapis/java-core/issues/110)) ([61e2d19](https://www.github.com/googleapis/java-core/commit/61e2d19bb4400978681aa018a8dc200214203830))


### Bug Fixes

* fix conversion for pre-epoch timestamps ([#160](https://www.github.com/googleapis/java-core/issues/160)) ([1f8b6b4](https://www.github.com/googleapis/java-core/commit/1f8b6b4835aaa702ec94bbbde89ed90f519c935a))


### Dependencies

* update dependency com.google.api:gax-bom to v1.54.0 ([#168](https://www.github.com/googleapis/java-core/issues/168)) ([5b52f9e](https://www.github.com/googleapis/java-core/commit/5b52f9e8d8cdc82b56114d3d1e857d137ae7ca98))
* update dependency io.grpc:grpc-bom to v1.27.2 ([#166](https://www.github.com/googleapis/java-core/issues/166)) ([28c9859](https://www.github.com/googleapis/java-core/commit/28c98595c9ee96760a063085bd85024177bd6dd2))

### [1.92.5](https://www.github.com/googleapis/java-core/compare/v1.92.4...v1.92.5) (2020-02-10)


### Dependencies

* update dependency com.google.api-client:google-api-client-bom to v1.30.8 ([#146](https://www.github.com/googleapis/java-core/issues/146)) ([1d9c7db](https://www.github.com/googleapis/java-core/commit/1d9c7db40502eff9723f27c24be31ecc2fac9c5d))
* update dependency com.google.protobuf:protobuf-bom to v3.11.3 ([#148](https://www.github.com/googleapis/java-core/issues/148)) ([092c69b](https://www.github.com/googleapis/java-core/commit/092c69bef5b10cf27ef6770e90ae8e50ea205dcd))

### [1.92.4](https://www.github.com/googleapis/java-core/compare/v1.92.3...v1.92.4) (2020-01-31)


### Dependencies

* update core dependencies ([#143](https://www.github.com/googleapis/java-core/issues/143)) ([454ce1d](https://www.github.com/googleapis/java-core/commit/454ce1dc5a6a41f3333a2a5303315cd9eb66f442))

### [1.92.3](https://www.github.com/googleapis/java-core/compare/v1.92.2...v1.92.3) (2020-01-28)


### Dependencies

* update dependency com.google.api:gax-bom to v1.53.0 ([#126](https://www.github.com/googleapis/java-core/issues/126)) ([bdb1bce](https://www.github.com/googleapis/java-core/commit/bdb1bceb63502c828a37dd50fdb3e0e2204fc0aa))
* update dependency com.google.auth:google-auth-library-bom to v0.20.0 ([#135](https://www.github.com/googleapis/java-core/issues/135)) ([f40c636](https://www.github.com/googleapis/java-core/commit/f40c6365b1b19bc3360b0094599311bc3271f0e5))
* update dependency com.google.http-client:google-http-client-bom to v1.34.1 ([#137](https://www.github.com/googleapis/java-core/issues/137)) ([9216702](https://www.github.com/googleapis/java-core/commit/92167026d8e4178ebb952490a3322bd685441a60))
* update dependency org.threeten:threetenbp to v1.4.1 ([82cac64](https://www.github.com/googleapis/java-core/commit/82cac64486352e46ddc6044a72fff6141d9b10ce))

### [1.92.2](https://www.github.com/googleapis/java-core/compare/v1.92.1...v1.92.2) (2020-01-09)


### Bug Fixes

* cast to proper interface ([#124](https://www.github.com/googleapis/java-core/issues/124)) ([cd6eabf](https://www.github.com/googleapis/java-core/commit/cd6eabffcdeed485aba088336fa473a6b85c752d)), closes [#123](https://www.github.com/googleapis/java-core/issues/123)

### [1.92.1](https://www.github.com/googleapis/java-core/compare/v1.92.0...v1.92.1) (2020-01-02)


### Dependencies

* update dependency com.google.errorprone:error_prone_annotations to v2.3.4 ([#105](https://www.github.com/googleapis/java-core/issues/105)) ([52f47c5](https://www.github.com/googleapis/java-core/commit/52f47c5ed84742b4b41417c486bfbb3c817b4a23))
* update dependency com.google.guava:guava-bom to v28.2-android ([#113](https://www.github.com/googleapis/java-core/issues/113)) ([8b11b1a](https://www.github.com/googleapis/java-core/commit/8b11b1a8d452ab823f35509ae42263c4a69f2a5a))
* update dependency com.google.http-client:google-http-client-bom to v1.34.0 ([#98](https://www.github.com/googleapis/java-core/issues/98)) ([d8e946d](https://www.github.com/googleapis/java-core/commit/d8e946dfd3866380406b02ad908925c4250cc34a))
* update dependency com.google.protobuf:protobuf-bom to v3.11.1 ([#106](https://www.github.com/googleapis/java-core/issues/106)) ([6d36434](https://www.github.com/googleapis/java-core/commit/6d364341bc5552e98590f9344b0e2d8cf4e68f0c))
* update dependency io.grpc:grpc-bom to v1.26.0 ([#107](https://www.github.com/googleapis/java-core/issues/107)) ([fca41a7](https://www.github.com/googleapis/java-core/commit/fca41a73fb6ca42eb4014d3cec6b32cc8e97ded9))

## [1.92.0](https://www.github.com/googleapis/java-core/compare/v1.91.3...v1.92.0) (2019-12-13)


### Features

* increase DEFAULT_CHUNK_SIZE to reduce transfer overhead ([#87](https://www.github.com/googleapis/java-core/issues/87)) ([09b327d](https://www.github.com/googleapis/java-core/commit/09b327daf764403b7800180cab79ae3e38815075))
* support setting ServiceOption for quota project ([#92](https://www.github.com/googleapis/java-core/issues/92)) ([6aa4476](https://www.github.com/googleapis/java-core/commit/6aa4476441fd7636dd116516d3bf4b738cf8a8a9))


### Dependencies

* update dependency com.google.api-client:google-api-client-bom to v1.30.5 ([#68](https://www.github.com/googleapis/java-core/issues/68)) ([e1a4047](https://www.github.com/googleapis/java-core/commit/e1a4047fb470ea4f80459ca0bb399f4ab2c7decf))
* update dependency com.google.api:gax-bom to v1.50.1 ([#73](https://www.github.com/googleapis/java-core/issues/73)) ([f493b5b](https://www.github.com/googleapis/java-core/commit/f493b5bbe5945202af6a94fe01407f795014b4a1))
* update dependency com.google.api:gax-bom to v1.51.0 ([#85](https://www.github.com/googleapis/java-core/issues/85)) ([71d0de7](https://www.github.com/googleapis/java-core/commit/71d0de782432814971facb7cbb67acdec5e45f00))
* update dependency com.google.auth:google-auth-library-bom to v0.19.0 ([#93](https://www.github.com/googleapis/java-core/issues/93)) ([b465630](https://www.github.com/googleapis/java-core/commit/b465630023dc87537a02a34fb957be340aeb6078))
* update dependency com.google.http-client:google-http-client-bom to v1.33.0 ([#71](https://www.github.com/googleapis/java-core/issues/71)) ([8f1e690](https://www.github.com/googleapis/java-core/commit/8f1e690611e98855d0eed26d7ef75120bccc862e))
* update dependency io.grpc:grpc-bom to v1.25.0 ([#72](https://www.github.com/googleapis/java-core/issues/72)) ([3a09fc7](https://www.github.com/googleapis/java-core/commit/3a09fc7c4ce73a3c4f144d0cd4da6d29a1664b75))
* update to threetenbp 1.4.0 ([#89](https://www.github.com/googleapis/java-core/issues/89)) ([5128bd4](https://www.github.com/googleapis/java-core/commit/5128bd45bae8cbb8540eccd683f5bba52783feef))

### [1.91.3](https://www.github.com/googleapis/java-core/compare/v1.91.2...v1.91.3) (2019-10-23)


### Bug Fixes

* try to keep autovalue out of the runtime time classpath ([#48](https://www.github.com/googleapis/java-core/issues/48)) ([0988c27](https://www.github.com/googleapis/java-core/commit/0988c27b01461a0b8c02ac0f9def5b409c56980c))


### Dependencies

* update dependency com.google.api:gax-bom to v1.49.1 ([#65](https://www.github.com/googleapis/java-core/issues/65)) ([131a0fd](https://www.github.com/googleapis/java-core/commit/131a0fd52bebdd217bdcb288374127cef7889692))
* update dependency com.google.api.grpc:proto-google-common-protos to v1.17.0 ([#50](https://www.github.com/googleapis/java-core/issues/50)) ([3ba5512](https://www.github.com/googleapis/java-core/commit/3ba55124247b82061781c4ae0acb08cec239afe4))
* update dependency com.google.auth:google-auth-library-bom to v0.18.0 ([#56](https://www.github.com/googleapis/java-core/issues/56)) ([ab25f15](https://www.github.com/googleapis/java-core/commit/ab25f153021f73b5f0ce5cc2cf0b53d42a1871c7))
* update dependency io.grpc:grpc-bom to v1.24.1 ([07fefbb](https://www.github.com/googleapis/java-core/commit/07fefbb38de93c2b3b5095bc5432bc5161bb7094))

### [1.91.2](https://www.github.com/googleapis/java-core/compare/v1.91.1...v1.91.2) (2019-09-30)


### Dependencies

* update dependency com.google.api.grpc:proto-google-iam-v1 to v0.13.0 ([#40](https://www.github.com/googleapis/java-core/issues/40)) ([4f500cc](https://www.github.com/googleapis/java-core/commit/4f500cc))
* update dependency io.grpc:grpc-bom to v1.24.0 ([#39](https://www.github.com/googleapis/java-core/issues/39)) ([7f6f780](https://www.github.com/googleapis/java-core/commit/7f6f780))
* update errorprone to 2.3.3 ([#43](https://www.github.com/googleapis/java-core/issues/43)) ([232694c](https://www.github.com/googleapis/java-core/commit/232694c))

### [1.91.1](https://www.github.com/googleapis/java-core/compare/v1.91.0...v1.91.1) (2019-09-25)


### Dependencies

* update dependency com.google.api-client:google-api-client-bom to v1.30.4 ([#34](https://www.github.com/googleapis/java-core/issues/34)) ([886eda3](https://www.github.com/googleapis/java-core/commit/886eda3))
* update dependency com.google.auth:google-auth-library-bom to v0.17.2 ([#35](https://www.github.com/googleapis/java-core/issues/35)) ([ae44c72](https://www.github.com/googleapis/java-core/commit/ae44c72))
* update dependency com.google.http-client:google-http-client-bom to v1.32.1 ([#31](https://www.github.com/googleapis/java-core/issues/31)) ([4bdf09b](https://www.github.com/googleapis/java-core/commit/4bdf09b))
* update dependency com.google.protobuf:protobuf-bom to v3.10.0 ([#27](https://www.github.com/googleapis/java-core/issues/27)) ([23e4c26](https://www.github.com/googleapis/java-core/commit/23e4c26))
* update guava to 28.1-android ([#32](https://www.github.com/googleapis/java-core/issues/32)) ([0279479](https://www.github.com/googleapis/java-core/commit/0279479))

## [1.91.0](https://www.github.com/googleapis/java-core/compare/v1.90.0...v1.91.0) (2019-09-18)


### Dependencies

* update dependency com.google.api-client:google-api-client-bom to v1.30.3 ([#21](https://www.github.com/googleapis/java-core/issues/21)) ([fcd67f8](https://www.github.com/googleapis/java-core/commit/fcd67f8))
* update opencensus packages to v0.24.0 ([#22](https://www.github.com/googleapis/java-core/issues/22)) ([4b21afa](https://www.github.com/googleapis/java-core/commit/4b21afa))


### Documentation

* fix Kokoro badge link ([19d79d6](https://www.github.com/googleapis/java-core/commit/19d79d6))
* fix README versions and CI Status table ([6e3ccf3](https://www.github.com/googleapis/java-core/commit/6e3ccf3))
* update README with a better project description ([#17](https://www.github.com/googleapis/java-core/issues/17)) ([018d4d5](https://www.github.com/googleapis/java-core/commit/018d4d5))


### Features

* add google-cloud-core-bom artifact ([#13](https://www.github.com/googleapis/java-core/issues/13)) ([3cb19a0](https://www.github.com/googleapis/java-core/commit/3cb19a0))
