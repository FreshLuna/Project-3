<script lang="ts">
  import ActivityCard from '$lib/components/ActivityCard.svelte';
  export let title: string = '';
  export let items: any[] = [];
  let scroller: HTMLElement;

  function scrollBy(amount: number) {
    scroller?.scrollBy({ left: amount, behavior: 'smooth' });
  }
</script>

<section class="row">
  <div class="rowHeader">
    <h3>{title}</h3>
    <div class="controls">
      <button on:click={() => scrollBy(-300)} aria-label="Scroll left">‹</button>
      <button on:click={() => scrollBy(300)} aria-label="Scroll right">›</button>
    </div>
  </div>

  <div class="scroller" bind:this={scroller}>
    {#each items as item (item.id ?? item.filename)}
      <ActivityCard activity={item} />
    {/each}
  </div>
</section>

<style>
.row { margin: 1rem 0; }
.rowHeader { display:flex; justify-content:space-between; align-items:center; margin-bottom:0.5rem; }
.scroller { display:flex; gap:1rem; overflow-x:auto; padding-bottom:0.5rem; scroll-snap-type:x mandatory; }
.scroller > * { scroll-snap-align:start; flex:0 0 auto; }
.controls button { background:transparent; border:1px solid #ccc; padding:6px 10px; border-radius:6px; cursor:pointer; }
.controls button:hover { background:#f3f3f3 }
</style>
