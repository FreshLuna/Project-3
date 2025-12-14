<script lang="ts">
  import { onMount } from "svelte";
  import CheckboxDropdown from "../../lib/CheckboxDropdown.svelte";
  import { locations, weekdays, ages, genders, tags, loadStores } from "$lib/types/filterStores";
  import { writable, get, type Writable } from "svelte/store";
  import { loadActivities } from "$lib/utils/LoadActivities";
  import type { Activity } from "$lib/types/Activities";
  import type { CheckboxItem, Filters } from "$lib/types/filterStores";
  import SpotsDisplay from "$lib/utils/ParticipantSpotsVisualizer.svelte";
  import { derived } from 'svelte/store';

  // activities store
  const activities = writable<Activity[]>([]);
  const solsikke = writable<CheckboxItem>({id: 0, label: "Solsikke", checked: false });
  const waitinglist = writable<CheckboxItem>({id: 0, label: "Venteliste", checked: false });

  const filteredActivities = derived(
  [activities, waitinglist, solsikke],
  ([$activities, $waitinglist, $solsikke]) => {
    return $activities.filter(a => {
      // Check waiting list condition
      const waitingCondition = $waitinglist.checked ? a.participantCount > 0 : true;

      // Check solsikke tag condition
      const solsikkeCondition = $solsikke.checked ? a.tags?.includes("Solsikke") : true;

      // Return only activities that meet both conditions
      return waitingCondition && solsikkeCondition;
    });
  }
);

  // Load activities from backend
  onMount(async () => {
    try {
      await loadStores();
      const loaded = await loadActivities("activities");
      activities.set(loaded);
    } catch (e) {
      console.error("Failed to load activities", e);
    }
  });

  // Extract labels from checked items
  function getChecked(store: Writable<CheckboxItem[]>): string[] {
    return get(store)
      .filter((item: CheckboxItem) => item.checked)
      .map((item: CheckboxItem) => item.label);
  }

  // Form submit
  async function handleSubmit(event: SubmitEvent) {
    event.preventDefault();

    const filters: Filters = {
        locations: getChecked(locations),
        weekdays: getChecked(weekdays),
        ages: getChecked(ages),
        genders: getChecked(genders),
        tags: getChecked(tags)
    };

    if (get(solsikke).checked) {
      filters.tags.push("Solsikke");
    }
  
    console.log("Sending filters:", filters);
    
    //smarter code
    const loaded = await loadActivities("filtered", filters);
    activities.set(loaded);
}
</script>

<div class="ActivityList">
  <h1 class="centered">Alle Aktiviteter</h1>

  <div class="filtersRow">
    <form onsubmit={handleSubmit}>
      <div class="filters">
        <CheckboxDropdown store={locations} label="Lokation" />
        <CheckboxDropdown store={weekdays} label="Ugedage" />
        <CheckboxDropdown store={ages} label="Alder" />
        <CheckboxDropdown store={genders} label="Køn" />
        <CheckboxDropdown store={tags} label="Tags" />
        <label class="solsikke-checkbox">
          <input type="checkbox" bind:checked={$solsikke.checked} />
          Solsikke
        </label>
        <label class="Waitinglist-checkbox">
          <input type="checkbox" bind:checked={$waitinglist.checked} />
          Uden Venteliste
        </label>
        <button type="submit" class="submit-btn">Vis filtrerede</button>
      </div>
    </form>
  </div>
</div>

<!-- ACTIVITIES -->
<div>
  <section class="dynamicActivities">
    {#if $filteredActivities.length === 0}
      <p>Loading dynamic activities...</p>
    {:else}
      <div class="ActivityList">
        {#each $filteredActivities as a}
          <a href={`/${a.id}`} class="activity-link">
            <div class="b">
              {#if a.imgUrl}
                <div class="img-container">
                  <img class="img" src={a.imgUrl} alt={a.title} />
                  <SpotsDisplay count={a.participantCount} />
                </div>
              {:else}
                <div class="img placeholder"></div>
              {/if}

              <h3>{a.title}</h3>
              <h5>{a.organization}</h5>
              <p>🗓️{a.formattedDate}</p>
              <p>🎂{a.age}</p>

            </div>
          </a>
        {/each}
      </div>
    {/if}
  </section>
</div>

<style>
  /* Filters */
  .filtersRow {
    padding: 30px 0;
    display: flex;
    justify-content: center;
  }

  .filters {
    display: flex;
    gap: 1rem;
    align-items: center;
    flex-wrap: wrap;
  }

  .submit-btn {
    margin-left: auto;
    background: #6e479b;
    color: white;
    border: 2px solid #6e479b;
    padding: 0.5rem 1rem;
    border-radius: 6px;
    font-weight: bold;
    cursor: pointer;
  }

  .submit-btn:hover {
    background: white;
    border: 2px solid #6e479b;
    color: #6e479b;
  }

  /* Activity cards */
  .b {
    border-radius: 16px;
    margin: 1%;
    float: left;
    width: 18%;
    min-height: 420px;
    background-color: #6e479b;
    color: #ffffff;
    padding: 1rem;
    box-sizing: border-box;
    transition: background-color 180ms ease, transform 140ms ease, box-shadow 180ms ease;
    text-decoration: none;
  }
  .img-container {
  position: relative; /* Needed for absolute positioning of overlay */
  display: inline-block; /* Ensures container wraps image */
  }

  .b .img {
    border-radius: 12px;
    width: 100%;
    height: 220px;
    display: block;
    margin-bottom: 0.75rem;
    object-fit: cover;
  }

  .placeholder {
    background: #ddd;
  }

  .b h3,
  .b h5,
  .b p {
    color: #ffffff;
    margin: 0.25rem 0;
    font-weight: 500;
  }

  .b h3 { font-size: 1.05rem; }
  .b h5 { font-size: 0.9rem; opacity: 0.95; }

  .b:hover,
  .b:focus-within {
    background-color: #5e3b85ff;
    transform: translateY(-6px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.18);
  }

  a.activity-link {
    text-decoration: none;
  }
  .centered {
    text-align: center;     
    font-size: 3rem;          
    font-weight: 900;         
  }

</style>
