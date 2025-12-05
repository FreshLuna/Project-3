<script lang="ts">
	import { onMount } from 'svelte';
	import Row from '$lib/components/Row.svelte';

	let activities: any[] = [];
	let loading = true;
	let row1: any[] = [];
	let row2: any[] = [];

	function pickRandomRows(items: any[]) {
		const copy = items.slice();
		for (let i = copy.length - 1; i > 0; i--) {
			const j = Math.floor(Math.random() * (i + 1));
			[copy[i], copy[j]] = [copy[j], copy[i]];
		}

		row1 = copy.slice(0, 5);
		row2 = copy.slice(5, 10);
	}

	async function loadActivities() {
		try {
			const res = await fetch('https://localhost:8443/server/activities');
			if (!res.ok) throw new Error('Failed');
			const data = await res.json();
			
			// Map backend Activity fields to component structure
			activities = data.map((item: any) => ({
				id: item.ActivityID,
				title: item.ActivityName,
				organization: item.ActivityOrganizer,
				date: item.DateAndTime,
				age: item.AgeGroup,
				imgUrl: null
			}));
			
			pickRandomRows(activities);
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
		<Row title="Recommended for you" items={row1} layout="grid-scroll" />
		<Row title="New & Popular" items={row2} layout="grid-scroll" />
	{/if}
</main>

<style>
	.page { padding: 1.5rem; font-family: Inter, system-ui, -apple-system, 'Segoe UI', Roboto, Arial; }
	h1 { margin: 0 0 1rem 0; }
</style>
