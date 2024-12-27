import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-invite-dialog',
  standalone: true,
  imports: [],
  templateUrl: './invite-dialog.component.html',
  styleUrls: ['./invite-dialog.component.scss'],
})
export class InviteDialogComponent {
  inviteCode: string;

  constructor(
    private dialogRef: MatDialogRef<InviteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { inviteCode: string }
  ) {
    this.inviteCode = data.inviteCode;
  }

  copyToClipboard(): void {
    navigator.clipboard.writeText(this.inviteCode).then(() => {
      alert('Invite code copied to clipboard!');
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
