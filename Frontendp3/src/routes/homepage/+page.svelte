<script lang="ts">
	import { onMount } from 'svelte';
	import Row from '$lib/components/Row.svelte';

	let activities: any[] = []; // Let's the activities remain empty until fetched from the server.
	let loading = true;
	let row1: any[] = []; // Makes two rows, that are initialised as empty before being filled.
	let row2: any[] = [];

	function pickRandomRows(items: any[], perRow = 6) { // Function that randomizes the activities into random rows.
		const copy = items.slice();
		for (let i = copy.length - 1; i > 0; i--) {
			const j = Math.floor(Math.random() * (i + 1));
			[copy[i], copy[j]] = [copy[j], copy[i]];
		}

		const take = Math.min(perRow, Math.ceil(copy.length / 2));
		row1 = copy.slice(0, take);
		row2 = copy.slice(take, take * 2);
	}

	async function loadActivities() { // Fetches the activities from the server. 
		try {
			const res = await fetch('https://localhost:8443/server/activities');
			if (!res.ok) throw new Error('Failed to fetch activities');
			activities = await res.json();
			pickRandomRows(activities, 6);
		} catch (err) {
			console.error('Error loading activities', err);
		} finally {
			loading = false;
		}
	}

	onMount(loadActivities);
</script>

<main class="page"> 
	<h1>Aalborg Try Out — Activities</h1>

	{#if loading}
		<p>Loading activities…</p>
	{:else}
		<Row title="Recommended for you" items={row1} />
		<Row title="New & Popular" items={row2} />
	{/if}
</main>

<style>
	.page { padding: 1.5rem; font-family: Inter, system-ui, -apple-system, 'Segoe UI', Roboto, Arial; }
	h1 { margin: 0 0 1rem 0; }
</style>