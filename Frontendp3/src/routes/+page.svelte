<script lang="ts">
    import { onMount } from 'svelte';
    import { loadActivities } from '$lib/utils/LoadActivities';
    import type { Activity } from '$lib/types/Activities';
    import ActivityCard from '$lib/components/ActivityCard.svelte';
    
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
            console.error('Failed to load activities');
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
    <h2>Afholdes Snart</h2>
    <div class="carousel-wrapper">
        <button class="nav left" on:click={() => scrollLeft(car1)}>‹</button>
        <div class="carousel" bind:this={car1}>
            <ActivityCard activities={upcomingActivities}/>
        </div>
        <button class="nav right" on:click={() => scrollRight(car1)}>›</button>
    </div>

    <h2>Populære Aktiviteter</h2>
    <div class="carousel-wrapper">
        <button class="nav left" on:click={() => scrollLeft(car2)}>‹</button>
        <div class="carousel" bind:this={car2}>
            <ActivityCard activities={popularActivities}/>
        </div>
        <button class="nav right" on:click={() => scrollRight(car2)}>›</button>
    </div>

    <h2>Nye Aktiviteter</h2>
    <div class="carousel-wrapper">
        <button class="nav left" on:click={() => scrollLeft(car3)}>‹</button>
        <div class="carousel" bind:this={car3}>
            <ActivityCard activities={newActivities}/>
        </div>
        <button class="nav right" on:click={() => scrollRight(car3)}>›</button>
    </div>

{/if}

<style>

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
    padding: 1rem, 0;
}

.carousel::-webkit-scrollbar {
    display: none;
}

.nav {
    background: var(--color-white);
    border: none;
    font-size: 2rem;
    padding: 0 0.5rem;
    cursor: pointer;
    z-index: 20;
}

.left { margin-right: 1rem; }
.right { margin-left: 1rem; }
</style>
