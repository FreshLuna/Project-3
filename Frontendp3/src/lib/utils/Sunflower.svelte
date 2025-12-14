<script lang="ts">
import solsikke from '$lib/assets/solsikke.png';
  export let tags: string[] | undefined;

  $: show = tags?.includes("Solsikke") ?? false;
</script>

<style>
    .sunflower-box {
    position: absolute;
    top: 4%;
    left: 4%;
    width: clamp(40px, 15%, 90px);
    z-index: 10;
    pointer-events: none;
  }

  .sunflower-image {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
</style>


{#if show}
<div class="sunflower-box">
  <img src={solsikke} alt="Solsikke" class="sunflower-image" />
</div>
{/if}