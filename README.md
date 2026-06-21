# Keepsake — Android app source

A private, offline family-recipe cookbook. Photograph a handwritten recipe
card, attach the story behind it, and build a cookbook you can hand down or
gift. No account, no server, no internet permission at all — everything is
stored locally in a Room database on the device.

## What's built

- **Cookbook screen** — list of all saved recipes, each shown as a card with
  its photo, contributor, and category tag
- **Add/edit screen** — camera capture for the recipe-card photo, plus fields
  for the story, ingredients, and instructions
- **Recipe detail screen** — full view with the photo, the story rendered as
  a pulled quote, and the ingredients/instructions
- **Local Room database** — all data stays on-device, nothing leaves the phone
- **Warm, heirloom-card visual design** — deep parchment background, a
  paprika/wine accent, and an italic serif used only for the recipe's story
  quote, so it reads like a note in the margin of a card

## What's not built yet (intentionally out of scope for v1)

- Voice memo recording (you can add this later as a second way to capture
  the story, alongside the text field)
- A "gift this cookbook" export/share flow
- App icon is a simple placeholder vector — swap in real artwork before
  publishing

## How to run it — phone-only, no computer needed

Android Studio is desktop software, so it can't run on a phone. Instead,
this project includes a GitHub Actions workflow that builds an installable
APK in the cloud — you trigger it from your phone's browser and download
the finished app.

1. **Create a free GitHub account** at github.com (if you don't have one)
2. **Create a new repository** — tap the "+" in the top right of the GitHub
   app or site → "New repository" → name it `keepsake` → Create
3. **Upload this project** — on the repository page, tap "Add file" → "Upload
   files", then upload everything inside this `Keepsake` folder (you can
   select multiple files/folders at once from your phone's file manager)
4. Go to the **Actions** tab on your repository — GitHub will detect the
   workflow file and start building automatically. It takes about 3–5
   minutes.
5. When it finishes (green checkmark), tap into the completed run, scroll
   down to **Artifacts**, and download `keepsake-debug-apk` — this is a zip
   containing the installable app
6. Unzip it (most phone file managers can do this, or use a free "zip
   extractor" app), then tap the `.apk` file inside to install it. Android
   will likely ask you to allow "install unknown apps" for your browser or
   file manager the first time — that's expected for any app installed
   outside the Play Store, including this kind of test build.

You now have Keepsake installed and running on your phone, fully testable.

## How to run it — if you do get access to a computer later

1. Install **Android Studio** (free): https://developer.android.com/studio
2. Open Android Studio → **Open** → select the `Keepsake` folder
3. Let it sync (first sync downloads Gradle + dependencies — needs internet)
4. Plug in your phone with USB debugging enabled, or use a virtual device
5. Click the green **Run** button

## Before publishing to Google Play

- Replace the placeholder launcher icon (`res/drawable/ic_launcher_*.xml`)
  with real artwork — a simple AI-generated or designed icon works fine
- Bundle real fonts if you want the exact type pairing described in the
  design (currently using system serif/sans as a placeholder so this builds
  immediately with zero extra setup) — drop `.ttf` files in `res/font` and
  update `Type.kt`
- Write a one-paragraph privacy policy (this app collects nothing, so it's a
  short one) and host it anywhere public — Play Console requires a URL
- Decide on the free/paid split — e.g. free up to 5 recipes, $9.99 one-time
  to unlock unlimited

## Architecture notes

- **Jetpack Compose** for the UI, **Room** for storage, **Coil** for loading
  recipe photos — all standard, well-supported libraries, nothing exotic
- No special Android permissions beyond camera — no accessibility service,
  no health data, no background services
- Fully offline — there is no networking code in this app at all, so there's
  nothing to break, rate-limit, or pay for as it scales
