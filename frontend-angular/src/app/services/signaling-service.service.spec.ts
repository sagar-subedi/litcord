import { TestBed } from '@angular/core/testing';

import { SignalingService } from '../services/signaling-service.service';

describe('SignalingServiceService', () => {
  let service: SignalingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SignalingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
