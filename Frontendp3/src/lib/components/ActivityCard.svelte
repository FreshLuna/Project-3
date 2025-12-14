<script lang="ts">
    import type { Activity } from '$lib/types/Activities';
    import SpotsDisplay from "$lib/utils/ParticipantSpotsVisualizer.svelte";
    import Sunflower from "$lib/utils/Sunflower.svelte";

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
                <p class="org">{activity.organization}</p>

            </div>
        </a>
    {/each}

<style>
.card { display:block; color:inherit; text-decoration:none; }
.poster {width: 33.33vw; flex-shrink: 0; text-decoration: none; color: inherit; position: relative;}
.poster img { display:block; width:100%; aspect-ratio: 16 / 8; object-fit:cover; border-radius: 10px;}
.card:hover .poster { transform: scale(1.02); transition: transform .18s ease;}
.meta { padding-top:0.5rem; }
.meta h4 { margin:0; font-size:0.95rem; }
.org { margin:0; color:var(--muted,#666); font-size:0.85rem; }
</style>
