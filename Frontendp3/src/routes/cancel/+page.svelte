<script lang="ts">
    import { page } from '$app/stores';
    import { onMount } from 'svelte';

    let firstname = '';
    let lastname = '';
    let email = '';
    let activityName = '';
    $: activityParam = $page.url.searchParams.get('activity');
    $: activityName = activityParam ? decodeURIComponent(activityParam) : '';

    async function submitCancel() {
        if (!firstname.trim() || !lastname.trim() || !email.trim()) {
            alert('Udfyld venligst fornavn, efternavn og email');
            return;
        }

        const payload = {
            firstname: firstname.trim(),
            lastname: lastname.trim(),
            email: email.trim().toLowerCase(),
            activity: activityName
        };

        try {
            const res = await fetch('https://localhost:8443/server/cancel', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            const text = await res.text();
            if (!res.ok) {
                alert('Afmelding mislykkedes: ' + (text || res.status));
                return;
            }

            alert('Afmelding sendt: ' + text);
            // reset
            firstname = '';
            lastname = '';
            email = '';
        } catch (err) {
            console.error(err);
            alert('Netværksfejl ved afmelding. Tjek server.');
        }
    }
</script>

<div class="container">
    <div class="inner">
        <h1>Afmeld fra aktivitet her</h1>
        <p>{activityName ? `Du afmelder dig fra: ${activityName}` : 'Vælg aktivitet fra forsiden for at forudfylde.'}</p>

        <form on:submit|preventDefault={submitCancel} class="form">
            <div class="row">
                <label for="firstname">Fornavn</label>
                <input id="firstname" bind:value={firstname} required />
            </div>
            <div class="row">
                <label for="lastname">Efternavn</label>
                <input id="lastname" bind:value={lastname} required />
            </div>
            <div class="row">
                <label for="email">Email</label>
                <input id="email" type="email" bind:value={email} required />
            </div>

            <div style="margin-top:1rem;">
                <button type="submit" class="btn">Bekræft afmelding</button>
            </div>
        </form>
    </div>
</div>

<style>
    .container{
        display:flex;
        align-items:center;
        justify-content:center;
        min-height:60vh;
        padding:2rem;
    }
    .inner{
        border:2px solid #222;
        border-radius:10px;
        padding:2rem 3rem;
        text-align:left;
        width:480px;
    }
    h1{ margin:0 0 1rem 0 }
    .row{ display:flex; flex-direction:column; margin-bottom:0.75rem }
    label{ font-weight:600; margin-bottom:0.25rem }
    input{ padding:0.5rem; border-radius:4px; border:1px solid #ccc }
    .btn{ background:#6E479B; color:white; padding:0.5rem 1rem; border-radius:6px; border:none; cursor:pointer }
</style>
