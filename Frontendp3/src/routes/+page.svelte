<!--Row 1-->
<div class="b">
    <img class="one" src={activity1} alt="Activity 1">
    <h3>Name of Activity</h3>
    <h5>Activity Holder</h5>
    <p>Date information</p>
    <p>Age</p>
</div>
<div class="b">
    <img class="one" src={activity2} alt="Activity 2">
    <h3>Name of Activity</h3>
    <h5>Activity Holder</h5>
    <p>Date information</p>
    <p>Age</p>
</div>
<div class="b">
    <img class="one" src={activity3} alt="Activity 3">
    <h3>Name of Activity</h3>
    <h5>Activity Holder</h5>
    <p>Date information</p>
    <p>Age</p>
</div>
<div class="b">
    <img class="one" src={activity4} alt="Activity 4">
    <h3>Name of Activity</h3>
    <h5>Activity Holder</h5>
    <p>Date information</p>
    <p>Age</p>
</div>
<div class="b">
    <img class="one" src={activity5} alt="Activity 5">
    <h3>Name of Activity</h3>
    <h5>Activity Holder</h5>
    <p>Date information</p>
    <p>Age</p>
</div>

<!--Row 2-->
<div class="b">
    <img class="one" src={activity6} alt="Activity 6">
    <h3>Name of Activity</h3>
    <h5>Activity Holder</h5>
    <p>Date information</p>
    <p>Age</p>
</div>
<div class="b">
    <img class="one" src={activity7} alt="Activity 7">
    <h3>Name of Activity</h3>
    <h5>Activity Holder</h5>
    <p>Date information</p>
    <p>Age</p>
</div>
<div class="b">
    <img class="one" src={activity8} alt="Activity 8">
    <h3>Name of Activity</h3>
    <h5>Activity Holder</h5>
    <p>Date information</p>
    <p>Age</p>
</div>
<div class="b">
    <img class="one" src={activity9} alt="Activity 9">
    <h3>Name of Activity</h3>
    <h5>Activity Holder</h5>
    <p>Date information</p>
    <p>Age</p>
</div>
<div class="b">
    <img class="one" src={activity10} alt="Activity 10">
    <h3>Name of Activity</h3>
    <h5>Activity Holder</h5>
    <p>Date information</p>
    <p>Age</p>
</div>

<style>
/* modern font stack applied globally */
:global(body) {
    font-family: Inter, ui-sans-serif, system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
    margin: 0;
    padding: 0;
}

.b {
    border-radius: 16px;
    display: inline-block;
    position: relative;
    margin: 1%;
    float: left;
    width: 18%;
    min-height: 420px;
    background-color: #6E479B;
    color: #ffffff; /* make text inside the card white */
    padding: 1rem;
    box-sizing: border-box;
}

.b .one {
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

.b h3 { font-size: 1.05rem; }
.b h5 { font-size: 0.9rem; opacity: 0.95; }

@media (max-width: 800px) {
  .b { width: 48%; }
}
@media (max-width: 480px) {
  .b { width: 100%; float: none; }
}
</style>

<script>
    import activity1 from '$lib/assets/activity1.avif'; // adjust extension/name
    import activity2 from '$lib/assets/activity2.avif';
    import activity3 from '$lib/assets/activity3.avif';
    import activity4 from '$lib/assets/activity4.avif';
    import activity5 from '$lib/assets/activity5.avif';
    import activity6 from '$lib/assets/activity6.avif';
    import activity7 from '$lib/assets/activity7.avif';
    import activity8 from '$lib/assets/activity8.avif';
    import activity9 from '$lib/assets/activity9.avif';
    import activity10 from '$lib/assets/activity10.avif';
</script>

