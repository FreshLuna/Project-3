<script lang="ts">
  export let activity: {
    id?: number | string;
    filename?: string;
    title?: string;
    organization?: string;
    date?: string;
    time?: string;
    age?: string;
    imgUrl?: string | null;
  } = {};
</script>

<a class="card" href={`/${activity.id ?? activity.filename ?? ''}`} aria-label={`Open ${activity.title ?? 'activity'}`}>
  <div class="poster">
    {#if activity.imgUrl}
      <img src={activity.imgUrl} alt={activity.title} loading="lazy" />
    {:else}
      <div class="placeholder"></div>
    {/if}
  </div>
  <div class="meta">
    <h4>{activity.title ?? `Activity ${activity.id ?? ''}`}</h4>
    <p class="org">{activity.organization}</p>
  </div>
</a>

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
