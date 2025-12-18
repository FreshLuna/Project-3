<script lang="ts">
    import type { Activity } from '$lib/types/Activities';
    import SpotsDisplay from "$lib/utils/ParticipantSpotsVisualizer.svelte";
    import Sunflower from "$lib/utils/Sunflower.svelte";
    import { Calendar, Cake } from 'lucide-svelte';


    export let activities: Activity[] = [];

</script>
<div class="grid">
    {#each activities as activity (activity.id)}
        <a class="card" href={`/${activity.id}`} aria-label={`Open ${activity.title}`}>
            <div class="poster">
                <img src={activity.imgUrl} alt={activity.title} loading="lazy" />
                <Sunflower tags={activity.tags} />
                <SpotsDisplay count={activity.participantCount} />
            </div>

            <div class="meta">
                <h4>{activity.title}</h4>
                <description>{activity.organization}</description> 
                <description><Calendar size="1rem"/> {activity.formattedDate}</description>
                <description><Cake size="1rem"/> {activity.age}</description>
            </div>
        </a>
    {/each}
</div>
<style>
.grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(15rem, 1fr)); gap: 3rem 1rem; min-width: calc(2 * 15rem + 2 * 1rem); max-width: calc(5 * 15rem + 5 * 1rem); padding: 1.5rem;}
.card { display:block; color:inherit; text-decoration:none; box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2); border-radius: 10px; background-color: var(--color-white);}
.poster { flex-shrink: 0; color: inherit; position: relative;}
.poster img { display:block; width:100%; aspect-ratio: 4 / 3 ; object-fit:cover; border-top-left-radius: 10px; border-top-right-radius: 10px;}
.card:hover { transform: scale(1.02); transition: .18s ease;}
.meta { padding:1rem; }
</style>
