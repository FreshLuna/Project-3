<script lang="ts">
    import { onMount } from 'svelte';
    import { loadActivities } from '$lib/utils/LoadActivities';
    import type { Activity } from '$lib/types/Activities';

    let activities: Activity[] = [];
    let loading = true;
    let error: string | null = null;
    export let activity: Activity | undefined;

    

    onMount(async () => {
        try {
            activities = await loadActivities();
        } catch (err) {
            error = 'Failed to load activities.';
            console.error(err);
        } finally {
            loading = false;
        }
    });
</script>

{#if loading}
    <p>Loading activities…</p>
{:else if error}
    <p>{error}</p>
{:else}
    {#each activities as activity (activity.id)}
        <a class="card" href={`/${activity.id}`} aria-label={`Open ${activity.title}`}>
            <div class="poster">
                {#if activity.imgUrl}
                    <img src={activity.imgUrl} alt={activity.title} loading="lazy" />
                {:else}
                    <div class="placeholder"></div>
                {/if}
            </div>

            <div class="meta">
                <h4>{activity.title}</h4>
                <p class="org">{activity.organization}</p>
            </div>
        </a>
    {/each}
{/if}

<style>
.card { display:block; width:200px; color:inherit; text-decoration:none; }
.poster { border-radius:8px; overflow:hidden; background:#eee; height:120px; }
.poster img { display:block; width:100%; height:100%; object-fit:cover; transition: transform .18s ease; }
.card:hover .poster img { transform: scale(1.06); }
.placeholder { width:100%; height:100%; background:linear-gradient(90deg,#eee,#ddd); }
.meta { padding-top:0.5rem; }
.meta h4 { margin:0; font-size:0.95rem; }
.org { margin:0; color:var(--muted,#666); font-size:0.85rem; }
</style>
