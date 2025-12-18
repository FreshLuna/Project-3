<script lang="ts">
  import FilterRow from '$lib/components/FilterRow.svelte';
  import ActivityCard from '$lib/components/ActivityCardPretty.svelte';
  import { filteredActivities } from '$lib/utils/GetFilteredActivities';
</script>

<div class="ActivityList">
  <h1 class="centered">Alle Aktiviteter</h1>
  <FilterRow />
</div>

<section class="dynamicActivities">
  {#if $filteredActivities.length === 0}
    <p>Loading dynamic activities...</p>
  {:else}
    <ActivityCard activities={$filteredActivities} />
  {/if}
</section>

<style>
.centered {
  margin-top: 3rem; margin-bottom: 2rem;
  text-align: center;
  font-size: 3rem;
  font-weight: 900;
}
.dynamicActivities {
  display: flex;
  justify-content: center;
}
</style>
