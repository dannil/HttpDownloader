SET version=1.0.0

cd ../

cov-build --dir cov-int mvn clean
cov-build --dir cov-int mvn install
cov-emit-java --dir cov-int --war target/httpdownloader-%version%.war

jar -cMf coverity-httpdownloader-%version%.zip cov-int