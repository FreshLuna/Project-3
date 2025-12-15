<script lang="ts">
  import { onMount } from "svelte";
  import CheckboxDropdown from "$lib/CheckboxDropdown.svelte";
  import {locations, weekdays, ages, genders, tags, loadStores, } from "$lib/types/filterStores";
  import { activities, solsikke, waitinglist,} from "$lib/utils/GetFilteredActivities";
  import { loadActivities } from "$lib/utils/LoadActivities";
  import type { Filters } from "$lib/types/filterStores";

  // Load activities initially
  onMount(async () => {
    try {
      await loadStores();
      const loaded = await loadActivities("activities");
      activities.set(loaded);
    } catch (e) {
      console.error("Failed to load activities", e);
    }
  });


  $: {
  const filters: Filters = {
    locations: $locations.filter(i => i.checked).map(i => i.label),
    weekdays: $weekdays.filter(i => i.checked).map(i => i.label),
    ages: $ages.filter(i => i.checked).map(i => i.label),
    genders: $genders.filter(i => i.checked).map(i => i.label),
    tags: $tags.filter(i => i.checked).map(i => i.label),
  };

  if ($solsikke.checked) filters.tags.push("Solsikke");

  filterActivities(filters);
}

  async function filterActivities(filters: Filters) {
    try {
      const loaded = await loadActivities("filtered", filters);
      activities.set(loaded);
    } catch (e) {
      console.error("Failed to load filtered activities", e);
    }
  }
</script>

<div class="filtersRow">
  <div class="filters">
    <CheckboxDropdown store={locations} label="Lokation" />
    <CheckboxDropdown store={weekdays} label="Ugedage" />
    <CheckboxDropdown store={ages} label="Alder" />
    <CheckboxDropdown store={genders} label="Køn" />
    <CheckboxDropdown store={tags} label="Tags" />
    <label>
      <input type="checkbox" bind:checked={$solsikke.checked} />
      Solsikke
    </label>
    <label>
      <input type="checkbox" bind:checked={$waitinglist.checked} />
      Uden Venteliste
    </label>
  </div>
</div>

<style>
  .filtersRow {
    padding: 0rem 1.5rem;
    display: flex;
    justify-content: center;
  }
  .filters {
    display: flex;
    gap: 1rem;
    flex-wrap: wrap;
  }
</style>
