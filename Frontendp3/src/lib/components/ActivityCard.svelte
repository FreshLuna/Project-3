<script lang="ts">
    import type { Activity } from '$lib/types/Activities';
    import SpotsDisplay from "$lib/utils/ParticipantSpotsVisualizer.svelte";
    import Sunflower from "$lib/utils/Sunflower.svelte";
    import { Calendar, Cake } from 'lucide-svelte';

    export let activities: Activity[] = [];

</script>
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
<style>
.card { color:inherit; text-decoration:none; background-color: var(--color-white); border-radius: 10px; margin: 1rem 1rem; box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);}
.poster { width: 32vw; flex-shrink: 0; position: relative; overflow: hidden;}
.poster img { display: block; width:100%; aspect-ratio: 16 / 8; object-fit:cover; border-radius: 10px; }
.card:hover { transform: scale(1.02); transition: .18s ease; }
.meta { padding: 1rem; }
</style>
