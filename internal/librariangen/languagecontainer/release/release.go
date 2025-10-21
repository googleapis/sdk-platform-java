package release

import "cloud.google.com/java/internal/librariangen/message"

// The ReleaseInitContext has the directory paths for the release-init command.
// https://github.com/googleapis/librarian/blob/main/doc/language-onboarding.md#release-init
type ReleaseInitContext struct {
	LibrarianDir string
	RepoDir      string
	OutputDir    string
}

type Config struct {
	Context *ReleaseInitContext
	// This request is parsed from the release-init-request.json file in
	// the LibrarianDir of the context.
	Request *message.ReleaseInitRequest
}
