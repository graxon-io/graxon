#
FROM alpine:3.21.3

#
COPY entrypoint.sh /entrypoint.sh

#
RUN chmod +x /entrypoint.sh

#
ENTRYPOINT ["/entrypoint.sh"]

#
CMD ["sh"]