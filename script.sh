#!/usr/bin/env bash

set -euo pipefail

SCRIPT_NAME="$(basename "$0")"
REPO_ROOT="$(cd "$(dirname "$0")" && pwd)"
SUMMARY_FILE="$REPO_ROOT/repository-summary.txt"

log() {
	printf '[%s] %s\n' "$SCRIPT_NAME" "$1"
}

count_files_by_extension() {
	local extension="$1"
	find "$REPO_ROOT" -type f -name "*.${extension}" | wc -l | tr -d ' '
}

check_path_exists() {
	local path="$1"
	local type="$2" # file|dir

	if [[ "$type" == "file" && -f "$path" ]]; then
		printf 'OK   %s\n' "$path"
	elif [[ "$type" == "dir" && -d "$path" ]]; then
		printf 'OK   %s\n' "$path"
	else
		printf 'MISS %s\n' "$path"
	fi
}

generate_summary() {
	local java_count py_count js_count xml_count properties_count sh_count

	java_count="$(count_files_by_extension "java")"
	py_count="$(count_files_by_extension "py")"
	js_count="$(count_files_by_extension "js")"
	xml_count="$(count_files_by_extension "xml")"
	properties_count="$(count_files_by_extension "properties")"
	sh_count="$(count_files_by_extension "sh")"

	{
		echo "Repository summary generated: $(date '+%Y-%m-%d %H:%M:%S')"
		echo "Root: $REPO_ROOT"
		echo
		echo "=== File counts by extension ==="
		echo "*.java       : $java_count"
		echo "*.py         : $py_count"
		echo "*.js         : $js_count"
		echo "*.xml        : $xml_count"
		echo "*.properties : $properties_count"
		echo "*.sh         : $sh_count"
		echo
		echo "=== Required structure check ==="
		check_path_exists "$REPO_ROOT/pom.xml" "file"
		check_path_exists "$REPO_ROOT/config.properties" "file"
		check_path_exists "$REPO_ROOT/config.xml" "file"
		check_path_exists "$REPO_ROOT/java" "dir"
		check_path_exists "$REPO_ROOT/python" "dir"
		check_path_exists "$REPO_ROOT/javascript" "dir"
		check_path_exists "$REPO_ROOT/empty_folder" "dir"
	} > "$SUMMARY_FILE"
}

print_help() {
	cat <<'EOF'
Usage:
	./script.sh [option]

Options:
	--summary   Generate repository summary file (repository-summary.txt)
	--check     Print required structure check to console
	--help      Show this help

Default behavior without options:
	Generate summary and print short status.
EOF
}

run_check_to_console() {
	log "Checking required repository structure..."
	check_path_exists "$REPO_ROOT/pom.xml" "file"
	check_path_exists "$REPO_ROOT/config.properties" "file"
	check_path_exists "$REPO_ROOT/config.xml" "file"
	check_path_exists "$REPO_ROOT/java" "dir"
	check_path_exists "$REPO_ROOT/python" "dir"
	check_path_exists "$REPO_ROOT/javascript" "dir"
	check_path_exists "$REPO_ROOT/empty_folder" "dir"
}

main() {
	case "${1:-}" in
		--summary)
			generate_summary
			log "Summary written to $SUMMARY_FILE"
			;;
		--check)
			run_check_to_console
			;;
		--help|-h)
			print_help
			;;
		"")
			generate_summary
			log "Summary written to $SUMMARY_FILE"
			;;
		*)
			log "Unknown option: $1"
			print_help
			exit 1
			;;
	esac
}

main "$@"
