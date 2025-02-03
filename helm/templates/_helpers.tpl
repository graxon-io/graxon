
{{/* Kubernetes standard labels */}}

{{- define "common.labels.core" -}}
app.kubernetes.io/name: {{ .Release.Name }}
app.kubernetes.io/instance: graxon
app.kubernetes.io/component: core
{{- end -}}

{{- define "common.labels.node" -}}
app.kubernetes.io/name: {{ .Release.Name }}
app.kubernetes.io/instance: graxon
app.kubernetes.io/component: node
{{- end -}}

{{- define "common.labels.notifier" -}}
app.kubernetes.io/name: {{ .Release.Name }}
app.kubernetes.io/instance: graxon
app.kubernetes.io/component: notifier
{{- end -}}

{{- define "common.labels.gateway" -}}
app.kubernetes.io/name: {{ .Release.Name }}
app.kubernetes.io/instance: graxon
app.kubernetes.io/component: gateway
{{- end -}}

{{- define "common.labels.admin" -}}
app.kubernetes.io/name: {{ .Release.Name }}
app.kubernetes.io/instance: graxon
app.kubernetes.io/component: admin
{{- end -}}


{{- define "common.labels.kafka-ui" -}}
app.kubernetes.io/name: {{ .Release.Name }}
app.kubernetes.io/instance: graxon
app.kubernetes.io/component: kafka-ui
{{- end -}}