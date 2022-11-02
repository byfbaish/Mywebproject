FROM websphere-liberty:22.0.0.6-full-java8-ibmjava
ARG GIT_URL=''
ARG GIT_BRANCH=''
ARG GIT_COMMIT=''

COPY --chown=1001:0 ImageBuild/defaultServer /config/
COPY --chown=1001:0 target/mywebapp.war /config/apps/


RUN chgrp -R 0 /config \
 && chmod -R g=u /config

LABEL aba.git.url=${ABA_GIT_URL} \
      aba.git.branch=${ABA_GIT_BRANCH} \
      aba.git.commit=${ABA_GIT_COMMIT}
