<script lang="ts">
    import { page } from '$app/stores';

    let firstname = '';
    let lastname = '';
    let email = '';
    let activityName = '';
    $: activityParam = $page.url.searchParams.get('activity');
    $: firstnameParam = $page.url.searchParams.get('firstname');
    $: lastnameParam = $page.url.searchParams.get('lastname');
    $: emailParam = $page.url.searchParams.get('email');


    $: activityName = activityParam ? decodeURIComponent(activityParam):"";
    $: displayName = activityName.replace(/\d+/g, '');

    $: if (firstnameParam) firstname = decodeURIComponent(firstnameParam);
    $: if (lastnameParam) lastname = decodeURIComponent(lastnameParam);
    $: if (emailParam) email = decodeURIComponent(emailParam);

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
            // firstname = '';
            // lastname = '';
            // email = '';
        } catch (err) {
            console.error(err);
            alert('Netværksfejl ved afmelding. Tjek server.');
        }
    }
</script>

<div class="container">
    <div class="inner">
        <h1>Afmeld fra aktivitet her</h1>
        <p>{activityName ? `Du afmelder dig fra: ${displayName}` : 'Vælg aktivitet fra forsiden for at forudfylde.'}</p>

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
    .row{ display:flex; flex-direction:column; margin-bottom:0.75rem }
    .btn{ background:var(--color-primary-purple); color:var(--color-white); padding:0.5rem 1rem; border-radius:6px; border:none; cursor:pointer }
</style>
