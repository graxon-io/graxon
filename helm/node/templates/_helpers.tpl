
{{/* Kubernetes standard labels */}}

{{- define "common.labels.node" -}}
app.kubernetes.io/name: {{ .Release.Name }}
app.kubernetes.io/instance: graxon
app.kubernetes.io/component: node
{{- end -}}
