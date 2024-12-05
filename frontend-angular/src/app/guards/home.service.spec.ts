import { TestBed } from '@angular/core/testing';

import { HomeGuard } from './home.guard';

describe('HomeGuard', () => {
  let service: HomeGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HomeGuard);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
