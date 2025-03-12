
{{/* Kubernetes standard labels */}}
{{/* ========================================================= */}}

{{- define "common.labels.default" -}}
app.kubernetes.io/managed-by: {{ .Release.Service }}
app.kubernetes.io/name: {{ .Release.Name }}
app.kubernetes.io/instance: graxon
{{- end -}}

{{- define "common.labels.selector" -}}
app.kubernetes.io/name: {{ .Release.Name }}
app.kubernetes.io/instance: graxon
{{- end -}}

{{/* Service labels */}}
{{/* ========================================================= */}}

{{- define "projects.labels.default" -}}
{{- include "common.labels.default" . }}
app.kubernetes.io/component: projects
{{- end -}}

{{- define "projects.labels.selector" -}}
{{- include "common.labels.selector" . }}
app.kubernetes.io/component: projects
{{- end -}}

{{- define "core.labels.default" -}}
{{- include "common.labels.default" . }}
app.kubernetes.io/component: core
{{- end -}}

{{- define "core.labels.selector" -}}
{{- include "common.labels.selector" . }}
app.kubernetes.io/component: core
{{- end -}}

{{- define "notifier.labels.default" -}}
{{- include "common.labels.default" . }}
app.kubernetes.io/component: notifier
{{- end -}}

{{- define "notifier.labels.selector" -}}
{{- include "common.labels.selector" . }}
app.kubernetes.io/component: notifier
{{- end -}}

{{/* Support labels */}}
{{/* ========================================================= */}}

{{- define "gateway.labels.default" -}}
{{- include "common.labels.default" . }}
app.kubernetes.io/component: gateway
{{- end -}}

{{- define "gateway.labels.selector" -}}
{{- include "common.labels.selector" . }}
app.kubernetes.io/component: gateway
{{- end -}}

{{- define "admin.labels.default" -}}
{{- include "common.labels.default" . }}
app.kubernetes.io/component: admin
{{- end -}}

{{- define "admin.labels.selector" -}}
{{- include "common.labels.selector" . }}
app.kubernetes.io/component: admin
{{- end -}}


{{/* Environment */}}
{{/* ========================================================= */}}

{{/* Spring boot profile selection */}}
{{- define "env.profiles" -}}
- name: SPRING_PROFILES_ACTIVE
  value: {{ .Values.profile }}
{{- end -}}

{{/* Default services environments */}}
{{- define "env.default" -}}
- name: GRAXON_RABBITMQ_HOST
  value: {{ .Release.Name }}-rabbitmq-headless
- name: GRAXON_OTEL_HOST
  value: {{ .Release.Name }}-otel
{{- end -}}

{{/* Actuator basic auth settings */}}
{{- define "env.actuator" -}}
- name: ACTUATOR_USERNAME
  valueFrom:
    configMapKeyRef:
      name: {{ .Release.Name }}-common
      key: actuator-username
- name: ACTUATOR_PASSWORD
  valueFrom:
    secretKeyRef:
      name: {{ .Release.Name }}-common
      key: actuator-password
{{- end -}}