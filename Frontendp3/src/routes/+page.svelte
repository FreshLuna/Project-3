<script>
    import { onMount } from 'svelte';

    // -------- Dynamic activities (runtime fetch from static/activities.txt) --------
    let activities = [];

    /* Build a lookup table of image filename -> resolved URL.
    We use Vite's import.meta.glob with { eager: true } so the modules
    are imported at build/SSR time and `mod.default` contains the final URL.
    Example: '../lib/assets/activity1.avif' -> { default: '/_app/immutable/..../activity1.abcd.avif' }*/
    const imagesGlob = import.meta.glob('../lib/assets/*.avif', { eager: true });

    // Convert the glob result into a simple map: { 'activity1.avif': '/url/to/activity1.avif', ... }
    const imageMap = Object.fromEntries(
        Object.entries(imagesGlob).map(([path, mod]) => [path.split('/').pop(), mod.default])
    );

    /**
     * Load activities from the static text file at /activities.txt
     * Expected file format (one activity per line):
     *   imageFileName|Activityname|Organization|Date|Age
     * Example:
     *   activity1.avif|Summer Camp|John Doe|tirsdag, 11. nov.|18+
     *
     * This function fetches the file, splits it into lines, trims empty lines,
     * and converts each line into an object used by the UI. If an image filename
     * from the file matches a key in imageMap, we attach the resolved URL to imgUrl;
     * otherwise imgUrl is null and the UI will show a placeholder.
     */
    async function loadActivities() {
        try {
            const res = await fetch('/activities.txt');
            if (!res.ok) {
                // If the file isn't available, log and exit early.
                console.error('Could not fetch /activities.txt', res.status);
                return;
            }

            const raw = await res.text();

            activities = raw
                // split into lines (support both Windows and Unix line endings)
                .split(/\r?\n/)
                // remove whitespace-only lines
                .map(l => l.trim())
                .filter(Boolean)
                // parse each line into an activity object
                .map(line => {
                    // split fields by pipe | and trim each field
                    const [img, title, organization, date, time, age] = line.split('|').map(s => s?.trim());
                    return {
                        imgFile: img, // original filename from the text file
                        imgUrl: imageMap[img] ?? null, // resolved URL or null if missing
                        title: title ?? '',
                        organization: organization ?? '',
                        date: date ?? '',
                        time: time ?? '',
                        age: age ?? ''
                    };
                });
        } catch (err) {
            // Any unexpected error (network, parsing, etc.) is logged for debugging
            console.error('Error loading activities:', err);
        }
    }

    // Run the loader when the component mounts in the browser
    onMount(loadActivities);
</script>

<head>
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
    />
</head>
<div class="homepage">
    <div class="frontPictureContainer"></div>

    <div class="activityMenu">
        <h1>Aalborg Try Out aktiviteter</h1>

        <div class="searchAndFilters">
            <input
                class="searchbar"
                type="text"
                placeholder="Search activities..."
            />

            <div class="column">
                <div class="dropdownContainer dropdownBordered">
                    <div class="dropdownToggle clickDropdown">
                        Dropdown menu
                    </div>
                    <div class="dropdownMenu">
                        <ul>
                            <li>Filter 1</li>
                            <li>Filter 2</li>
                            <li>Filter 3</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- DYNAMIC ACTIVITIES -->
    <section class="dynamicActivities">
        {#if activities.length === 0}
            <p>Loading dynamic activities...</p>
        {:else}
            <div class="ActivityList">
                {#each activities as a}
                    <div class="b">
                        {#if a.imgUrl}
                            <img class="img" src={a.imgUrl} alt={a.title} />
                        {:else}
                            <div class="img" style="background:#ddd;border-radius:12px;height:220px;margin-bottom:.75rem;"></div>
                        {/if}
                        <h3>{a.title}</h3>
                        <h5>{a.organization}</h5>
                        <p>🗓️{a.date} {a.time}</p>
                        <p>🎂{a.age}</p>
                    </div>
                {/each}
            </div>
        {/if}
    </section>

</div>

<!-- CSS STYLE -->
<style>
    .frontPicture {
        height: 300px;
        display: block;
    }

    .frontPictureContainer {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 1rem;
    }
    .searchAndFilters {
        padding-top: 30px;
        padding-bottom: 30px;
        display: flex;
    }

    .homepage {
        padding-left: 50px;
        padding-right: 50px;
        padding-top: 30px;
        align-items: center;
    }

    .searchbar {
        flex: 1;
        padding: 10px;
        font-size: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
        margin-right: 20px;
    }

    /* font stack applied globally */
    :global(body) {
        font-family:
            Inter,
            ui-sans-serif,
            system-ui,
            -apple-system,
            "Segoe UI",
            Roboto,
            "Helvetica Neue",
            Arial,
            sans-serif;
        margin: 0;
        padding: 0;
    }

    /* Dropdown menu CSS start*/
    .column {
        max-width: 100%;
        width: 100%;
        padding: 0 15px;
        flex: 0 0 100%;
    }

    .dropdownContainer {
        margin: 0 auto;
        margin-bottom: 20px;
    }

    @media (min-width: 767px) {
        flex: 0 0 50%;
        max-width: 50%;
    }

    @media (min-width: 992px) {
        flex: 0 0 25%;
        max-width: 25%;
    }

    /* Dropdown menu CSS slut*/

    .b {
        border-radius: 16px;
        display: inline-block;
        position: relative;
        margin: 1%;
        float: left;
        width: 18%;
        min-height: 420px;
        background-color: #6e479b;
        color: #ffffff; /* make text inside the card white */
        padding: 1rem;
        box-sizing: border-box;
    }

    .b .img {
        border-radius: 12px;
        width: 100%;
        /* enforce a consistent image height so all cards look the same */
        height: 220px;
        display: block;
        margin-bottom: 0.75rem;
        object-fit: cover; /* crop/scale image to fill the box */
    }

    .b h3,
    .b h5,
    .b p {
        color: #ffffff;
        margin: 0.25rem 0;
        font-weight: 500;
    }

    .b h3 {
        font-size: 1.05rem;
    }
    .b h5 {
        font-size: 0.9rem;
        opacity: 0.95;
    }

    /* hover highlight; add a small lift and shadow for emphasis */
    .b {
        transition:
            background-color 180ms ease,
            transform 140ms ease,
            box-shadow 180ms ease;
    }
    .b:hover,
    .b:focus-within {
        background-color: #5e3b85ff;
        transform: translateY(-6px);
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.18);
        cursor: pointer;
    }

    @media (max-width: 800px) {
        .b {
            width: 48%;
        }
    }
    @media (max-width: 480px) {
        .b {
            width: 100%;
            float: none;
            transform: none;
        }
    }
</style>
