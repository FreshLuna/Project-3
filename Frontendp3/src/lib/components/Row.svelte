<script lang="ts">
	import ActivityCard from '$lib/components/ActivityCard.svelte';

	export let title: string = '';
	export let items: any[] = [];
	export let layout: 'grid-scroll' | 'netflix' | 'grid' = 'grid-scroll';

	let scroller: HTMLElement;

	function scrollByItems(direction: number) {
		const card = scroller.querySelector('.card');
		if (!card) return;
		const width = card.getBoundingClientRect().width;
		scroller.scrollBy({ left: width * direction, behavior: 'smooth' });
	}
</script>

<section class="row">
	<div class="rowHeader">
		<h3>{title}</h3>

		{#if layout === 'grid-scroll' || layout === 'netflix'}
			<div class="controls">
				<button on:click={() => scrollByItems(-1)} aria-label="Left">‹</button>
				<button on:click={() => scrollByItems(1)} aria-label="Right">›</button>
			</div>
		{/if}
	</div>

	{#if layout === 'grid-scroll'}
		<div class="scrollGrid" bind:this={scroller}>
			{#each items as item (item.id ?? item.filename)}
				<div class="cardWrapper">
					<div class="card"><ActivityCard activity={item} /></div>
				</div>
			{/each}
		</div>
	{:else if layout === 'netflix'}
		<div class="scroller" bind:this={scroller}>
			{#each items as item (item.id ?? item.filename)}
				<div class="card"><ActivityCard activity={item} /></div>
			{/each}
		</div>
	{:else}
		<div class="grid">
			{#each items as item (item.id ?? item.filename)}
				<ActivityCard activity={item} />
			{/each}
		</div>
	{/if}
</section>

<style>
.row { margin: 1rem 0; }
.rowHeader { display:flex; justify-content:space-between; align-items:center; margin-bottom:0.5rem; }

/* centered three-visible grid with scroll */
.scrollGrid {
	display:flex;
	overflow:hidden;
	gap:2rem;
	justify-content:center;
	padding:0 2rem;
}
.cardWrapper {
	flex:0 0 calc((100% - 4rem) / 3);
	display:flex;
	justify-content:center;
}
.card {
	width:100%;
}

/* netflix row */
.scroller {
	display:flex;
	gap:1rem;
	overflow-x:auto;
	padding-bottom:0.5rem;
	scroll-snap-type:x mandatory;
}
.scroller > * {
	scroll-snap-align:start;
	flex:0 0 auto;
}

/* grid fallback */
.grid {
	display:flex;
	justify-content:space-between;
	gap:1rem;
}
.grid > * {
	flex:1 1 0;
}

.controls button {
	background:transparent;
	border:1px solid #ccc;
	padding:6px 10px;
	border-radius:6px;
	cursor:pointer;
}
.controls button:hover { background:#f3f3f3 }
</style>