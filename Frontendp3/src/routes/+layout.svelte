<script lang="ts">
	let { children } = $props();
</script>

<nav>
	<a href="/">home</a>
	<a href="/login">login</a>
	<a href="/opret">opret</a>
	<a href="/aktivitet1">eksempel 1</a>
	<a href="/aktivitet2">eksempel 2</a>
	<a href="/aktivitet3">eksempel 3</a>
</nav>

{@render children()}
