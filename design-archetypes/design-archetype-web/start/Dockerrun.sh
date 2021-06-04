docker run -it -d \
           -p 8080:8080 \
           --restart=always \
           --name design-web \
           design-web:1.0.0