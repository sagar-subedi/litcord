import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../../services/auth.service';
import { Membership } from '../../../../types/Membership';

@Component({
  selector: 'app-manage-members-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './manage-members-modal.component.html',
  styleUrl: './manage-members-modal.component.scss'
})
export class ManageMembersModalComponent implements OnInit{
  @Input() members: Membership[] = []; // List of members
  @Input() isAdmin: boolean = false;
  @Output() removeMember = new EventEmitter<number>(); // Emit member ID to delete
  @Output() close = new EventEmitter<void>(); // Close the dialog

  currentUserId : string | null = null; 
  
  constructor(private authService: AuthService){
    
  }

  ngOnInit(): void {
      this.currentUserId = this.authService.getCurrentUserId();
  }
  closeModal() {
    this.close.emit();
  }

  deleteMember(membership: Membership) {
    if (confirm('Are you sure you want to remove this member?')) {
      this.removeMember.emit(membership.membershipId);
    }
  }
}
