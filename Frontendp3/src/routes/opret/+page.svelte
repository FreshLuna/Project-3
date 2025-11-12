<h1>Opret din forening</h1>
<p>Få nye medlemmer gennem nem digital tildmelding til prøvetimer og automatiseret opfølgning</p>

<body>
    <div class="container">
        <form id="opretForm">
            <div class="form-group">
                <label for="foreningnavn">Foreningens navn</label>
                <input type="text" id="foreningnavn" name="forening" required>
            </div>
                <div class="form-group">
                <label for="cvrnum">Foreningens CVR-nummer</label>
                <input type="text" id="cvr" name="cvrnum" required>
            </div>
                <div class="form-group">
                <label for="kontaktperson">Dit fulde navn (kontaktperson)</label>
                <input type="text" id="kontaktperson" name="kontaktperson" required>
            </div>
                <div class="form-group">
                <label for="tlf">Telefon</label>
                <input type="number" id="tlf" name="tlfnum" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
                <div class="password-hint" id="passwordHint">
                </div>
                <span id="passwordError" class="error"></span>
                <div class="password-strength" id="passwordStrength"></div>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword"
                    name="confirmPassword" required>
                <span id="confirmPasswordError" class="error"></span>
            </div>
            <div class="form-group">
                <label for="kommune">Kommune</label>
                <input type="text" id="kommune" name="kommune" required>
            </div>
            <button type="submit">Opret forening</button>
        </form>
        <p id="message"></p>
    </div>
    </body>
