<script lang="ts">
    import { onMount } from 'svelte';
    import { loadActivities } from '$lib/utils/LoadActivities';
    import type { Activity } from '$lib/types/Activities';

    let upcomingActivities: Activity[] = [];
    let popularActivities: Activity[] = [];
    let newActivities: Activity[] = [];
    let loading = true;
    let error: string | null = null;

    // Carousel refs
    let car1: HTMLDivElement;
    let car2: HTMLDivElement;
    let car3: HTMLDivElement;

    function scrollLeft(carousel: HTMLDivElement) {
        carousel.scrollBy({ left: -window.innerWidth / 3, behavior: 'smooth' });
    }

    function scrollRight(carousel: HTMLDivElement) {
        carousel.scrollBy({ left: window.innerWidth / 3, behavior: 'smooth' });
    }

    onMount(async () => {
        try {
            upcomingActivities = await loadActivities("activities");
            popularActivities = await loadActivities("popularActivities");
            newActivities = await loadActivities("newActivities");
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

    <h2>UpcomingActivities</h2>
    <div class="carousel-wrapper">
        <button class="nav left" on:click={() => scrollLeft(car1)}>‹</button>
        <div class="carousel" bind:this={car1}>
            {#each upcomingActivities as activity (activity.id)}
                <a class="slide" href={`/${activity.id}`}>
                    {#if activity.imgUrl}
                        <img src={activity.imgUrl} alt={activity.title} loading="lazy" />
                    {:else}
                        <div class="placeholder"></div>
                    {/if}
                    <h4>{activity.title}</h4>
                    <p>{activity.organization}</p>
                </a>
            {/each}
        </div>
        <button class="nav right" on:click={() => scrollRight(car1)}>›</button>
    </div>

    <h2>Popular Activities</h2>
    <div class="carousel-wrapper">
        <button class="nav left" on:click={() => scrollLeft(car2)}>‹</button>
        <div class="carousel" bind:this={car2}>
            {#each popularActivities as activity (activity.id + '-2')}
                <a class="slide" href={`/${activity.id}`}>
                    {#if activity.imgUrl}
                        <img src={activity.imgUrl} alt={activity.title} loading="lazy" />
                    {:else}
                        <div class="placeholder"></div>
                    {/if}
                    <h4>{activity.title}</h4>
                    <p>{activity.organization}</p>
                </a>
            {/each}
        </div>
        <button class="nav right" on:click={() => scrollRight(car2)}>›</button>
    </div>

    <h2>New Activities</h2>
    <div class="carousel-wrapper">
        <button class="nav left" on:click={() => scrollLeft(car3)}>‹</button>
        <div class="carousel" bind:this={car3}>
            {#each newActivities as activity (activity.id + '-3')}
                <a class="slide" href={`/${activity.id}`}>
                    {#if activity.imgUrl}
                        <img src={activity.imgUrl} alt={activity.title} loading="lazy" />
                    {:else}
                        <div class="placeholder"></div>
                    {/if}
                    <h4>{activity.title}</h4>
                    <p>{activity.organization}</p>
                </a>
            {/each}
        </div>
        <button class="nav right" on:click={() => scrollRight(car3)}>›</button>
    </div>

{/if}

<style>
h2 {
    margin: 1.5rem 0 0.5rem;
}

.carousel-wrapper {
    position: relative;
    width: 100%;
    display: flex;
    align-items: center;
    margin-bottom: 2rem;
}

.carousel {
    display: flex;
    overflow-x: auto;
    scroll-behavior: smooth;
    gap: 1rem;
    padding: 1rem 0;
}

.carousel::-webkit-scrollbar {
    display: none;
}

/* Each item = 1/3 of the screen */
.slide {
    width: 33.33vw;
    flex-shrink: 0;
    text-decoration: none;
    color: inherit;
}

.slide img, .placeholder {
    width: 100%;
    height: 300px;
    border-radius: 10px;
    object-fit: cover;
    background: #eee;
}

h4 {
    margin: 0.4rem 0 0;
    font-size: 1rem;
}

p {
    margin: 0;
    font-size: 0.85rem;
    color: #666;
}

.nav {
    background: white;
    border: none;
    font-size: 2rem;
    padding: 0 0.5rem;
    cursor: pointer;
    z-index: 50;
}

.left { margin-right: 0.5rem; }
.right { margin-left: 0.5rem; }
</style>
